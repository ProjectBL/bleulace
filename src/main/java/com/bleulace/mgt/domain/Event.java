package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.Converters;
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
import com.bleulace.utils.jpa.LocalDateTimeRange;

@RooJavaBean
@Entity
@Converters(value = {
		@Converter(name = "localDateTimeConverter", converterClass = LocalDateTimeConverter.class),
		@Converter(name = "periodConverter", converterClass = PeriodConverter.class) })
public class Event extends Project implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = 8727887519388582258L;

	@Embedded
	private LocalDateTimeRange range;

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
		apply(command, EventCreatedEvent.class);
	}

	public void on(EventCreatedEvent event)
	{
		super.on(event);
		range = new LocalDateTimeRange(event.getStart(), event.getEnd());
		String creatorId = event.getCreatorId();
		if (creatorId != null)
		{
			new NewsFeedEnvelope().addFriends(event.getCreatorId())
					.withPayloads(event, this).send();
		}
	}

	public void handle(ResizeEventCommand command)
	{
		reschedule(command.getStart(), command.getEnd());
	}

	public void handle(MoveEventCommand command)
	{
		LocalDateTimeRange newRange = range.move(command.getStart());
		reschedule(newRange.getStart(), newRange.getEnd());
	}

	public void on(EventRescheduledEvent event)
	{
		range = event.getRange();
		new NewsFeedEnvelope().addAccounts(getManagers())
				.addAccounts(getAttendees()).withPayloads(this, event).send();
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

	private void reschedule(Date start, Date end)
	{
		if (!identicalDates(start, end))
		{
			apply(new EventRescheduledEvent(new LocalDateTimeRange(start, end)));
		}
	}

	private boolean identicalDates(Date start, Date end)
	{
		return range == null ? false : range.equals(new LocalDateTimeRange(
				start, end));
	}
}