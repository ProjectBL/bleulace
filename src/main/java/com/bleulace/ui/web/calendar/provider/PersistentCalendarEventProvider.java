package com.bleulace.ui.web.calendar.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.bleulace.mgt.presentation.EventDTO;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

class PersistentCalendarEventProvider implements CalendarEventProvider
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6347274883985342751L;

	private final String ownerId;

	private final String viewerId;

	PersistentCalendarEventProvider(String ownerId, String viewerId)
	{
		Assert.notNull(ownerId);
		Assert.notNull(viewerId);

		this.ownerId = ownerId;
		this.viewerId = viewerId;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		if (ownerId == null)
		{
			throw new IllegalStateException(
					"accountId is null. This is an invalid configuration");
		}
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		for (EventDTO dto : EventDTO.FINDER.findByAccountIdAndRange(ownerId,
				startDate, endDate))
		{
			for (EventDTOProcessor processor : CalendarViewType
					.acquireProcessors(ownerId, viewerId))
			{
				processor.process(dto);
			}
			events.add(dto);
		}
		return events;
	}
}