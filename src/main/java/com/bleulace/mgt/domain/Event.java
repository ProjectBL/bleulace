package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.Converters;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.feed.NewsFeedEnvelope;
import com.bleulace.mgt.application.command.CreateEventCommand;
import com.bleulace.mgt.application.command.InviteGuestsCommand;
import com.bleulace.mgt.application.command.MoveEventCommand;
import com.bleulace.mgt.application.command.ResizeEventCommand;
import com.bleulace.mgt.application.command.RsvpCommand;
import com.bleulace.mgt.domain.event.EventCreatedEvent;
import com.bleulace.mgt.domain.event.EventRescheduledEvent;
import com.bleulace.mgt.domain.event.GuestInvitedEvent;
import com.bleulace.mgt.domain.event.GuestRSVPEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.persistence.infrastructure.LocalDateTimeConverter;
import com.bleulace.persistence.infrastructure.PeriodConverter;
import com.bleulace.utils.jpa.EntityManagerReference;
import com.mysema.query.annotations.PropertyType;
import com.mysema.query.annotations.QueryType;

@RooJavaBean
@Entity
@Converters(value = {
		@Converter(name = "localDateTimeConverter", converterClass = LocalDateTimeConverter.class),
		@Converter(name = "periodConverter", converterClass = PeriodConverter.class) })
public class Event extends Project implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = 8727887519388582258L;

	@Column(nullable = false)
	@Convert("localDateTimeConverter")
	private LocalDateTime start;

	@QueryType(PropertyType.COMPARABLE)
	@Column(nullable = false)
	@Convert("periodConverter")
	private Period length;

	@Column(nullable = false)
	private String location;

	@ManyToMany
	private List<Account> attendees = new ArrayList<Account>();

	Event()
	{
	}

	public Event(CreateEventCommand command)
	{
		super(command.getId());
		apply(command, EventCreatedEvent.class);
	}

	public void handle(ResizeEventCommand command)
	{
		if (!(command.getStart().equals(getStart().toDate()) && command
				.getEnd().equals(getStart().plus(getLength()).toDate())))
		{
			EventRescheduledEvent event = new EventRescheduledEvent();
			event.setId(getId());
			event.setStart(LocalDateTime.fromDateFields(command.getStart()));
			event.setLength(Period.fieldDifference(
					LocalDateTime.fromDateFields(command.getStart()),
					LocalDateTime.fromDateFields(command.getEnd())));
			apply(event);
		}
	}

	public void handle(MoveEventCommand command)
	{
		EventRescheduledEvent event = new EventRescheduledEvent();
		event.setId(getId());
		event.setStart(LocalDateTime.fromDateFields(command.getNewStart()));
		event.setLength(getLength());
		apply(event);
	}

	public void on(EventRescheduledEvent event)
	{
		map(event);
		new NewsFeedEnvelope().addAccounts(getManagers())
				.addAccounts(getAttendees()).withPayloads(this, event).send();
	}

	public void on(EventCreatedEvent event)
	{
		super.on(event);
		start = LocalDateTime.fromDateFields(event.getStart());
		LocalDateTime end = LocalDateTime.fromDateFields(event.getEnd());
		length = Period.fieldDifference(start, end);
		String creatorId = event.getCreatorId();
		if (creatorId != null)
		{
			new NewsFeedEnvelope().addFriends(event.getCreatorId())
					.withPayloads(event, this).send();
		}
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
			attendees.add(EntityManagerReference.load(Account.class,
					event.getGuestId()));
			new NewsFeedEnvelope().addFriends(event.getGuestId())
					.withPayloads(this, event).send();
		}
	}
}