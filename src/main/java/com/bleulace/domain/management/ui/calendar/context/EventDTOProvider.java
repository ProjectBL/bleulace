package com.bleulace.domain.management.ui.calendar.context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.presentation.EventFinder;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeListener;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeNotifier;

@Configurable
class EventDTOProvider implements CalendarEventProvider,
		EventSetChangeNotifier, EventSetChangeListener
{
	private List<EventSetChangeListener> listeners = new ArrayList<EventSetChangeListener>();

	private final CalendarViewContext context;

	@Autowired
	private EventFinder finder;

	EventDTOProvider(CalendarViewContext context)
	{
		this.context = context;
		context.addEventSetChangeListener(this);
	}

	@Override
	public List<CalendarEvent> getEvents(Date start, Date end)
	{
		List<EventDTO> dtos = finder.findByAccountForDates(
				context.getOwnerId(), start, end);
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		for (EventDTO dto : dtos)
		{
			context.decorate(dto);
			events.add(dto);
		}
		return events;
	}

	@Override
	public void addEventSetChangeListener(EventSetChangeListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeEventSetChangeListener(EventSetChangeListener listener)
	{
		listeners.remove(listener);
	}

	@Override
	public void eventSetChange(EventSetChangeEvent changeEvent)
	{
		for (EventSetChangeListener listener : listeners)
		{
			listener.eventSetChange(changeEvent);
		}
	}
}
