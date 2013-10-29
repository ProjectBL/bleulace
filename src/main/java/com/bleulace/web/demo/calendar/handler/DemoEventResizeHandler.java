package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

@Component
class DemoEventResizeHandler extends BasicEventResizeHandler
{

	@Autowired
	private EventDAO eventDAO;

	@Override
	public void eventResize(EventResize event)
	{
		super.eventResize(event);
		eventDAO.save((PersistentEvent) event.getCalendarEvent());
	}
}
