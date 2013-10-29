package com.bleulace.web.demo.calendar.handler;

import org.springframework.stereotype.Component;

import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

@Component
class DemoDateClickHandler extends BasicDateClickHandler
{
	@Override
	public void dateClick(DateClickEvent event)
	{
		super.dateClick(event);
	}
}
