package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.feed.NewsFeedEnvelope;
import com.bleulace.mgt.application.command.CreateEventCommand;
import com.bleulace.mgt.application.command.EditEventCommand;
import com.bleulace.mgt.application.command.InviteGuestsCommand;
import com.bleulace.mgt.application.command.MoveEventCommand;
import com.bleulace.mgt.application.command.ResizeEventCommand;
import com.bleulace.mgt.application.command.RsvpCommand;
import com.bleulace.mgt.domain.event.EventCreatedEvent;
import com.bleulace.mgt.domain.event.EventEditedEvent;
import com.bleulace.mgt.domain.event.GuestInvitedEvent;
import com.bleulace.mgt.domain.event.GuestRSVPEvent;
import com.bleulace.mgt.presentation.ScheduleStatus;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.utils.jpa.DateWindow;
import com.bleulace.utils.jpa.EntityManagerReference;

@RooJavaBean
@Entity
public class Event extends Project implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = 8727887519388582258L;

	@Embedded
	private DateWindow window;

	@Column(nullable = false)
	private String location;

	@ManyToMany
	private List<Account> attendees = new ArrayList<Account>();

	Event()
	{
	}

	public Event(CreateEventCommand command)
	{
		setId(command.getId());
		apply(command, new EventCreatedEvent(command.getWindow()));
		String creatorId = command.getCreatorId();
		if (creatorId != null)
		{
			apply(new GuestInvitedEvent(getId(), creatorId));
			apply(new GuestRSVPEvent(getId(), creatorId, true));
		}
	}

	public void on(EventCreatedEvent event)
	{
		super.on(event);
		String creatorId = event.getCreatorId();
		if (creatorId != null)
		{
			new NewsFeedEnvelope().addFriends(event.getCreatorId())
					.withPayloads(event, this).send();
		}
	}

	public void handle(EditEventCommand command)
	{
		EventEditedEvent event = new EventEditedEvent(command.getWindow());
		SpringApplicationContext.getBean(ModelMapper.class).map(command, event);
		apply(event);
	}

	public void on(EventEditedEvent event)
	{
		setLocation(event.getLocation());
		setTitle(event.getTitle());
		setLocation(event.getLocation());
		setWindow(event.getWindow());
	}

	public void handle(ResizeEventCommand command)
	{
		window = new DateWindow(command.getStart(), command.getEnd());
	}

	public void handle(MoveEventCommand command)
	{
		window = window.move(command.getStart());
	}

	public void handle(InviteGuestsCommand command)
	{
		for (String guestId : command.getGuestIds())
		{
			apply(new GuestInvitedEvent(getId(), guestId));
		}
	}

	public void on(GuestInvitedEvent event)
	{
		new NewsFeedEnvelope().addFriends(event.getGuestId())
				.withPayloads(this, event).send();
	}

	public void handle(RsvpCommand command)
	{
		apply(new GuestRSVPEvent(getId(), command.getGuestId(),
				command.isAccepted()));
	}

	public void on(GuestRSVPEvent event)
	{
		if (event.isAccepted())
		{
			if (ScheduleStatus.BUSY.is(event.getGuestId(), window.getRange()))
			{
				throw new SchedulingConflictException();
			}
			attendees.add(EntityManagerReference.load(Account.class,
					event.getGuestId()));
			new NewsFeedEnvelope().addFriends(event.getGuestId())
					.withPayloads(this, event).send();
		}
	}

	public Date getStart()
	{
		return window.getStart();
	}

	public Date getEnd()
	{
		return window.getEnd();
	}
}