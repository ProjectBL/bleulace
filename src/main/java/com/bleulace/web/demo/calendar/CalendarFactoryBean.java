package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

@Scope("prototype")
@Component("calendar")
class CalendarFactoryBean implements FactoryBean<Calendar>
{
	private final String id;

	@Autowired
	private EventResizeHandler eventResizeHandler;

	@Autowired
	private EventMoveHandler eventMoveHandler;

	@Autowired
	@Qualifier("demoEventRightClickHandler")
	private Handler rightClickHandler;

	@Autowired
	private RangeSelectHandler rangeSelectHandler;

	@Autowired
	private EventClickHandler eventClickHandler;

	@Autowired
	private ApplicationContext ctx;

	CalendarFactoryBean(String id)
	{
		this.id = id;
	}

	private CalendarFactoryBean()
	{
		this(null);
	}

	@Override
	public Calendar getObject() throws Exception
	{
		Calendar calendar = new Calendar();
		calendar.setEventProvider((CalendarEventProvider) ctx.getBean(
				"demoCalendarEventProvider", id));
		calendar.setHandler(eventResizeHandler);
		calendar.setHandler(eventMoveHandler);
		calendar.setHandler(rangeSelectHandler);
		calendar.setHandler(eventClickHandler);
		calendar.addActionHandler(rightClickHandler);
		calendar.setImmediate(true);
		return calendar;
	}

	@Override
	public Class<?> getObjectType()
	{
		return Calendar.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}