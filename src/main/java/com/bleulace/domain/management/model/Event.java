package com.bleulace.domain.management.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.PreRemove;

import org.axonframework.domain.MetaData;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.command.CancelEventCommand;
import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.command.EditEventCommand;
import com.bleulace.domain.management.command.InviteGuestsCommand;
import com.bleulace.domain.management.command.RescheduleEventCommand;
import com.bleulace.domain.management.command.RsvpCommand;
import com.bleulace.domain.management.event.EventCanceledEvent;
import com.bleulace.domain.management.event.EventCreatedEvent;
import com.bleulace.domain.management.event.GuestInvitedEvent;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.jpa.DateWindow;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.jpa.config.QueryFactory;
import com.bleulace.utils.dto.Mapper;

@Entity
@RooJavaBean
public class Event extends Project
{
	@Embedded
	private DateWindow window;

	@MapKeyColumn(name = "GUEST_ID")
	@EventSourcedMember
	@ElementCollection
	private Map<Account, EventInvitee> invitees = new HashMap<Account, EventInvitee>();

	@Column(nullable = false)
	private String location = "";

	Event()
	{
	}

	public Event(CreateEventCommand command, MetaData metaData)
	{
		EventCreatedEvent event = new EventCreatedEvent();
		event.setId(getId());
		Mapper.map(command, event);
		apply(event, metaData);

		command.getInviteeIds().add(metaData.getSubjectId());
		updateInvitees(new HashSet<String>(command.getInviteeIds()), metaData);
		apply(new RsvpCommand(getId(), true), metaData);

		apply(new ManagerAssignedEvent(getId(), metaData.getSubjectId(),
				ManagementLevel.OWN), metaData);
	}

	private Event(CreateEventCommand command, MetaData metaData, String foo)
	{
		String creatorId = metaData.getSubjectId();
		EventCreatedEvent event = new EventCreatedEvent();
		event.setId(getId());
		Mapper.map(command, event);
		apply(event, metaData);
		if (creatorId != null)
		{
			ManagerAssignedEvent assignment = new ManagerAssignedEvent(getId(),
					creatorId, ManagementLevel.OWN);
			apply(assignment, metaData);
			apply(new RsvpCommand(getId(), true), metaData);
		}
		for (String accountId : command.getInviteeIds())
		{
			if (!invitees.containsKey(accountId))
			{
				apply(new GuestInvitedEvent(getId(), accountId), metaData);
			}
		}
	}

	public void on(EventCreatedEvent event)
	{
		setTitle(event.getTitle());
		this.location = event.getLocation();
		window = new DateWindow(event.getStart(), event.getEnd());
	}

	public void handle(InviteGuestsCommand command, MetaData metaData)
	{
		for (String accountId : command.getAccountIds())
		{
			apply(new GuestInvitedEvent(getId(), accountId), metaData);
		}
	}

	public void on(GuestInvitedEvent event, MetaData metaData)
	{
		String hostId = metaData.getSubjectId();
		Account host = hostId == null ? null : EntityManagerReference.load(
				Account.class, hostId);

		Account guest = EntityManagerReference.load(Account.class,
				event.getAccountId());

		invitees.put(guest, new EventInvitee(guest, host));
	}

	public void handle(EditEventCommand command, MetaData metaData)
	{
		apply(command, metaData);
		updateInvitees(new HashSet<String>(command.getInviteeIds()), metaData);
	}

	public void handle(RsvpCommand command, MetaData metaData)
	{
		apply(new RsvpCommand(command.isAccepted()), metaData);
	}

	public void handle(RescheduleEventCommand command, MetaData metaData)
	{
		apply(command, metaData);
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

	public void handle(CancelEventCommand command, MetaData metaData)
	{
		apply(Mapper.map(command, EventCanceledEvent.class), metaData);
	}

	public void on(EventCanceledEvent event)
	{
		flagForDeletion();
	}

	public RsvpStatus getRsvpStatus(String accountId)
	{
		EventInvitee invitee = invitees.get(EntityManagerReference.load(
				Account.class, accountId));
		return invitee == null ? null : invitee.getStatus();
	}

	@PreRemove
	protected void preRemove()
	{
		for (Account a : invitees.keySet())
		{
			invitees.remove(a);
		}
	}

	private void updateInvitees(Set<String> inviteeIds, MetaData metaData)
	{
		Set<String> alreadyInvited = new HashSet<String>(getInviteeIds());
		for (String inviteeId : inviteeIds)
		{
			if (!alreadyInvited.contains(inviteeId))
			{
				apply(new GuestInvitedEvent(getId(), inviteeId), metaData);
			}
		}
	}

	private List<String> getInviteeIds()
	{
		if (getId() == null)
		{
			return new ArrayList<String>();
		}

		QEvent e = QEvent.event;
		QEventInvitee i = QEventInvitee.eventInvitee;
		return QueryFactory.from(e).innerJoin(e.invitees, i)
				.where(e.id.eq(getId())).list(i.guest.id);
	}
}