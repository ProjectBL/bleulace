package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.web.annotation.WebProfile;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

@WebProfile
@Configuration
class CalendarConfig
{
	@Bean
	@Scope("ui")
	public Calendar calendar(CalendarEventProvider eventProvider,
			EventClickHandler eventClickHandler,
			RangeSelectHandler rangeSelectHandler,
			DateClickHandler dateClickHandler,
			EventMoveHandler eventMoveHandler,
			EventResizeHandler eventResizeHandler,
			@Qualifier("demoEventRightClickHandler") Handler actionHandler)
	{
		Calendar bean = new Calendar(eventProvider);
		bean.setHandler(eventClickHandler);
		bean.setHandler(rangeSelectHandler);
		bean.setHandler(dateClickHandler);
		bean.setHandler(eventMoveHandler);
		bean.setHandler(eventResizeHandler);
		bean.addActionHandler(actionHandler);
		bean.setImmediate(true);
		return bean;
	}
}
