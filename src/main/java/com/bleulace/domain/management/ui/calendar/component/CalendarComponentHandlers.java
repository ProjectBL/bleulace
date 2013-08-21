package com.bleulace.domain.management.ui.calendar.component;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Range;

import com.bleulace.domain.management.ui.calendar.component.CalendarComponent.CalendarComponentListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;

public interface CalendarComponentHandlers extends EventClickHandler,
		EventMoveHandler, EventResizeHandler, RangeSelectHandler,
		ForwardHandler, BackwardHandler, DateClickHandler, WeekClickHandler
{
	public void setComponentListeners(List<CalendarComponentListener> listeners);

	public void datesChanged(Range<Date> oldDates, Range<Date> newDates);

	public void doDateChangeNotification(Date oldStart, Date oldEnd,
			Date newStart, Date newEnd);
}