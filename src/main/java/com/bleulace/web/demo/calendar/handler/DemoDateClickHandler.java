package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

@Component
class DemoDateClickHandler extends BasicDateClickHandler
{
	@Autowired
	private ApplicationContext ctx;

	@Override
	public void dateClick(DateClickEvent event)
	{
		super.dateClick(event);
		ctx.getBean(TabSheet.class).setSelectedTab(0);
	}
}
