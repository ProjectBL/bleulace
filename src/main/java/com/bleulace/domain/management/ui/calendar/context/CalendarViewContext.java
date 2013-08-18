package com.bleulace.domain.management.ui.calendar.context;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public interface CalendarViewContext
{
	public String getOwnerId();

	public String getViewerId();

	public void decorate(EventDTO dto);

	public CalendarEventProvider getEventProvider();

	public CalendarHandlers getHandlers();

	public interface CalendarHandlers extends EventResizeHandler,
			EventMoveHandler, EventClickHandler, DateClickHandler,
			RangeSelectHandler
	{
	}
}