package com.bleulace.domain.management.ui.calendar.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.presentation.EventFinder;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

@Component("eventProvider")
@Scope("prototype")
class EventDTOProvider implements CalendarEventProvider
{
	@Autowired
	private EventFinder finder;

	@Autowired
	private EventDTOProcessorFactory factory;

	private String viewerId;

	private String ownerId;

	EventDTOProvider(String ownerId, String viewerId)
	{
		setContext(ownerId, viewerId);
	}

	@Override
	public List<CalendarEvent> getEvents(Date start, Date end)
	{
		Assert.notNull(viewerId);
		Assert.notNull(ownerId);
		EventDTOProcessor processor = factory.make(ownerId, viewerId);
		List<EventDTO> dtos = finder.findByAccountIdForDates(ownerId, start,
				end);
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		for (EventDTO dto : dtos)
		{
			processor.process(dto);
			events.add(dto);
		}
		return events;
	}

	public void setContext(String ownerId, String viewerId)
	{
		Assert.notNull(ownerId);
		Assert.notNull(viewerId);
		this.ownerId = ownerId;
		this.viewerId = viewerId;
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
