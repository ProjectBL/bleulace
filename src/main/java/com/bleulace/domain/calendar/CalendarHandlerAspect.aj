package com.bleulace.domain.calendar;

import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;

privileged aspect CalendarHandlerAspect
{
	after(MoveEvent event) : 
		execution(public void EventMoveHandler+.eventMove(MoveEvent))
		&& args(event)
	{
	}
}