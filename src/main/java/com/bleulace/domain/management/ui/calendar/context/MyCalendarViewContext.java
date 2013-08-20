package com.bleulace.domain.management.ui.calendar.context;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.crm.presentation.UserDTOFinder;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.handlers.DelegatingHandlers;
import com.vaadin.ui.components.calendar.event.CalendarEditableEventProvider;

@Configurable
class MyCalendarViewContext implements CalendarViewContext
{
	@Autowired
	private UserDTOFinder finder;

	private final String ownerId;

	private final Handlers handlers = new DelegatingHandlers();

	MyCalendarViewContext(String ownerId)
	{
		this.ownerId = ownerId;
	}

	@Override
	public String getOwnerId()
	{
		return ownerId;
	}

	@Override
	public String getViewerId()
	{
		return ownerId;
	}

	@Override
	public void decorate(EventDTO dto)
	{
		dto.setStyleName(dto.getRsvpStatus(ownerId).getStyleName());
	}

	@Override
	public CalendarEditableEventProvider getEventProvider()
	{
		return new EventDTOProvider(this);
	}

	@Override
	public TimeZone getTimeZone()
	{
		return finder.findById(ownerId).getTimeZone();
	}

	@Override
	public Handlers getHandlers()
	{
		return handlers;
	}
}
