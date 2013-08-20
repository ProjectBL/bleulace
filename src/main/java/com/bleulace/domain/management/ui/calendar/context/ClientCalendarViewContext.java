package com.bleulace.domain.management.ui.calendar.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.Calendar;

class ClientCalendarViewContext extends MyCalendarViewContext
{
	@Autowired
	@Qualifier("clientCalendar")
	private Calendar calendar;

	private final String viewerId;

	ClientCalendarViewContext(String ownerId, String viewerId)
	{
		super(ownerId);
		this.viewerId = viewerId;
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
}