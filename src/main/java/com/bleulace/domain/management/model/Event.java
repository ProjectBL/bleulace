package com.bleulace.domain.management.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;

import org.axonframework.common.annotation.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.ShiroMetaData;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.command.RescheduleEventCommand;
import com.bleulace.domain.management.command.RsvpCommand;
import com.bleulace.domain.management.event.EventCreatedEvent;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.utils.jpa.DateWindow;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean
public class Event extends Project
{
	@Embedded
	private DateWindow window = DateWindow.defaultValue();

	@ManyToMany
	private Set<Account> attendees = new HashSet<Account>();

	Event()
	{
	}

	public Event(CreateEventCommand command,
			@MetaData(ShiroMetaData.SUBJECT_ID) String creatorId)
	{
		EventCreatedEvent event = new EventCreatedEvent();
		event.setId(getId());
		event.setCreatorId(creatorId);
		mapper().map(command, event);
		apply(event);
		if (creatorId != null)
		{
			ManagerAssignedEvent assignment = new ManagerAssignedEvent();
			assignment.setId(getId());
			assignment.setAssignerId(creatorId);
			assignment.setAssigneeId(creatorId);
			assignment.setRole(ManagementRole.OWN);
			apply(assignment);
		}
	}

	public void on(EventCreatedEvent event)
	{
		map(event);
	}

	public void handle(RsvpCommand command)
	{
		apply(command);
	}

	public void on(RsvpCommand event)
	{
		Account account = EntityManagerReference.load(Account.class,
				event.getAccountId());
		if (event.isAccepted())
		{
			attendees.add(account);
		}
		else
		{
			attendees.remove(account);
		}
	}

	public void handle(RescheduleEventCommand command)
	{
		apply(command);
	}

	public void on(RescheduleEventCommand event)
	{
		if (event.getEnd() == null)
		{
			window = window.move(event.getStart());
		}
		else
		{
			window = new DateWindow(event.getStart(), event.getEnd());
		}
		// TODO : process scheduling conflicts
	}

	@PreRemove
	protected void preRemove()
	{
		for (Account a : attendees)
		{
			attendees.remove(a);
		}
	}
}