package com.bleulace.domain.management.ui.calendar.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.presentation.EventFinder;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeNotifier;

@Configurable
class EventDTOProvider implements CalendarEventProvider, EventChangeListener,
		EventSetChangeNotifier
{
	private List<EventSetChangeListener> listeners = new ArrayList<EventSetChangeListener>();

	@Autowired
	private EventFinder finder;

	@Autowired
	private EventDTOProcessorFactory factory;

	private String viewerId;

	private String ownerId;

	EventDTOProvider()
	{
	}

	@Override
	public List<CalendarEvent> getEvents(Date start, Date end)
	{
		Assert.notNull(viewerId);
		Assert.notNull(ownerId);
		EventDTOProcessor processor = factory.make(ownerId, viewerId);
		List<EventDTO> dtos = finder.findByAccountForDates(ownerId, start, end);
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		for (EventDTO dto : dtos)
		{
			processor.process(dto);
			dto.addEventChangeListener(this);
			events.add(dto);
		}
		return events;
	}

	public void setOwnerId(String ownerId)
	{
		Assert.notNull(ownerId);
		this.ownerId = ownerId;
	}

	public void setViewerId(String viewerId)
	{
		Assert.notNull(viewerId);
		this.viewerId = viewerId;
	}

	@Override
	public void eventChange(EventChangeEvent changeEvent)
	{
		fireEventSetChange();
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

	protected void fireEventSetChange()
	{
		EventSetChangeEvent event = new EventSetChangeEvent(this);

		for (EventSetChangeListener listener : listeners)
		{
			listener.eventSetChange(event);
		}
	}

	interface EventDTOProcessor
	{
		void process(EventDTO dto);
	}

	interface EventDTOProcessorFactory
	{
		EventDTOProcessor make(String ownerId, String viewerId);
	}
}
