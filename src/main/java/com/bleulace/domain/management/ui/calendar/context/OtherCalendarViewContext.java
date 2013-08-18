package com.bleulace.domain.management.ui.calendar.context;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

class OtherCalendarViewContext implements CalendarViewContext
{
	@Autowired
	private OtherCalendarViewHandlers handlers;

	private final String ownerId;
	private final String viewerId;

	OtherCalendarViewContext(String ownerId, String viewerId)
	{
		this.ownerId = ownerId;
		this.viewerId = viewerId;
	}

	@Override
	public String getOwnerId()
	{
		return ownerId;
	}

	@Override
	public String getViewerId()
	{
		return viewerId;
	}

	@Override
	public void decorate(EventDTO dto)
	{
	}

	@Override
	public CalendarEventProvider getEventProvider()
	{
		return new EventDTOProvider(this);
	}

	@Override
	public CalendarHandlers getHandlers()
	{
		return handlers;
	}
}
