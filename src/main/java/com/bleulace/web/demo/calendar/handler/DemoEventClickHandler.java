package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;

@Component
class DemoEventClickHandler implements EventClickHandler
{
	@Autowired
	private ApplicationContext ctx;

	@Override
	public void eventClick(EventClick event)
	{
	}
}
