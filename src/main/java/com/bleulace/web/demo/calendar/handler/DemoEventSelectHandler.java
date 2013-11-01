package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.RequiresAuthorization;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

@Component
class DemoEventSelectHandler implements RangeSelectHandler, EventClickHandler
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private AccountDAO dao;

	@Autowired
	private SystemUser user;

	@Override
	@RequiresAuthorization(types = "calendar", actions = "create")
	public void rangeSelect(RangeSelectEvent event)
	{
		PersistentEvent calendarEvent = new PersistentEvent();
		calendarEvent.setStart(event.getStart());
		calendarEvent.setEnd(event.getEnd());
		showEvent(calendarEvent, event.getComponent());
	}

	@Override
	public void eventClick(EventClick event)
	{
		showEvent((PersistentEvent) event.getCalendarEvent(),
				event.getComponent());
	}

	private void showEvent(PersistentEvent event, Calendar calendar)
	{
		Window w = (Window) ctx.getBean("timeBox", calendar, event);
		UI.getCurrent().addWindow(w);
		w.focus();
	}
}
