package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;

@Component
class DemoEventMoveHandler extends BasicEventMoveHandler
{
	@Autowired
	private EventDAO eventDAO;

	@Override
	public void eventMove(MoveEvent event)
	{
		super.eventMove(event);
		eventDAO.save((PersistentEvent) event.getCalendarEvent());
		event.getComponent().markAsDirty();
	}
}
