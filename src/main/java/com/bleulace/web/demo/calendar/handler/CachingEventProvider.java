package com.bleulace.web.demo.calendar.handler;

import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public interface CachingEventProvider extends CalendarEventProvider
{
	public boolean containsRange(CalendarDateRange range);
}