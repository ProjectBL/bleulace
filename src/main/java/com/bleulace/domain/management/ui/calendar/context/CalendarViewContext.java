package com.bleulace.domain.management.ui.calendar.context;

import java.io.Serializable;
import java.util.TimeZone;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public interface CalendarViewContext extends Serializable
{
	public String getOwnerId();

	public String getViewerId();

	public void decorate(EventDTO dto);

	public TimeZone getTimeZone();

	public CalendarEventProvider getEventProvider();

	public Handlers getHandlers();

	public interface Handlers extends DateClickHandler, EventMoveHandler,
			EventResizeHandler, RangeSelectHandler
	{
	}
}