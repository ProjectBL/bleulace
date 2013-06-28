package com.bleulace.domain.calendar.command;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.Command;
import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.JPACalendarEvent;
import com.bleulace.domain.calendar.ParticipationStatus;
import com.frugalu.api.messaging.jpa.EntityManagerReference;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

@Configurable
public class RescheduleEventCommand implements Command
{
	private final Command delegate;

	private CalendarEvent event;

	public RescheduleEventCommand(MoveEvent event)
	{
		delegate = new MoveEventCommand(event);
		this.event = event.getCalendarEvent();
	}

	public RescheduleEventCommand(EventResize event)
	{
		delegate = new ResizeEventCommand(event);
		this.event = event.getCalendarEvent();
	}

	@Override
	@Transactional
	public void execute()
	{
		delegate.execute();
		if (event instanceof JPACalendarEvent)
		{
			JPACalendarEvent jpaCalendarEvent = (JPACalendarEvent) event;
			for (Account account : jpaCalendarEvent.getParticipants().keySet())
			{
				if (jpaCalendarEvent.getParticipants().get(account)
						.equals(ParticipationStatus.ACCEPTED))
				{
					jpaCalendarEvent.getParticipants().put(account,
							ParticipationStatus.PENDING);
				}
			}

			Account current = Account.current();
			if (current != null)
			{
				jpaCalendarEvent.getParticipants().put(current,
						ParticipationStatus.ACCEPTED);
			}
			EntityManagerReference.get().merge(jpaCalendarEvent);
		}
	}
}