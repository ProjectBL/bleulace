package com.bleulace.domain.management.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

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
import com.bleulace.domain.management.event.GuestInvitationEvent;
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

		apply(new GuestInvitationEvent(this, metaData.getSubjectId(), true),
				metaData);
		apply(new RsvpCommand(getId(), true), metaData);
		apply(new ManagerAssignedEvent(getId(), metaData.getSubjectId(),
				ManagementLevel.OWN), metaData);
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
			apply(new GuestInvitationEvent(this, accountId, true), metaData);
		}
	}

	public void on(GuestInvitationEvent event, MetaData metaData)
	{
		Account guest = EntityManagerReference.load(Account.class,
				event.getAccountId());

		if (event.isInvited() && !invitees.containsKey(guest))
		{
			String hostId = metaData.getSubjectId();
			Account host = hostId == null ? null : EntityManagerReference.load(
					Account.class, hostId);
			invitees.put(guest, new EventInvitee(guest, host));
		}
	}

	public void handle(EditEventCommand command, MetaData metaData)
	{
		apply(command, metaData);
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
		flagForDeletion(true);
	}

	public RsvpStatus getRsvpStatus(String accountId)
	{
		EventInvitee invitee = invitees.get(EntityManagerReference.load(
				Account.class, accountId));
		return invitee == null ? null : invitee.getStatus();
	}

	@PrePersist
	@PreUpdate
	protected void preSave()
	{
		if (!isDeleted())
		{
			flagForDeletion(invitees.isEmpty());
		}
	}

	@PreRemove
	protected void preRemove()
	{
		for (Account a : invitees.keySet())
		{
			invitees.remove(a);
		}
	}

	private void updateManagers(Map<String, ManagementLevel> map,
			MetaData metaData)
	{
		for (Entry<String, ManagementLevel> entry : map.entrySet())
		{
			apply(new ManagerAssignedEvent(getId(), entry.getKey(),
					entry.getValue()), metaData);
		}
	}

	private void updateInvitees(Set<String> inviteeIds, MetaData metaData)
	{
		for (String id : inviteeIds)
		{
			changeGuestList(id, true, metaData);
		}
		Set<String> toRemove = getInviteeIds();
		toRemove.removeAll(inviteeIds);
		for (String id : toRemove)
		{
			changeGuestList(id, false, metaData);
		}
	}

	private void changeGuestList(String guestId, boolean attending,
			MetaData metaData)
	{
		apply(new GuestInvitationEvent(this, guestId, attending), metaData);
	}

	public Set<String> getInviteeIds()
	{
		if (getId() == null)
		{
			return new HashSet<String>();
		}

		QEvent e = QEvent.event;
		QEventInvitee i = QEventInvitee.eventInvitee;
		return new HashSet<String>(QueryFactory.from(e)
				.innerJoin(e.invitees, i).where(e.id.eq(getId()))
				.list(i.guest.id));
	}
}