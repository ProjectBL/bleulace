package com.bleulace.domain.management.ui.calendar.model;

import java.util.Date;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;

public interface EventModelFactory
{
	public EventModelAdapter<?> make(Date start, Date end,
			CalendarViewContext ctx);

	public EventModelAdapter<?> make(EventDTO dto, CalendarViewContext ctx);
}