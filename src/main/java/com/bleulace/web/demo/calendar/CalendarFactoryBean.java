package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.utils.DefaultIdCallback;
import com.bleulace.utils.IdCallback;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

@Lazy
@Scope("prototype")
@Component("calendar")
class CalendarFactoryBean implements FactoryBean<Calendar>
{
	private final IdCallback callback;

	@Autowired
	private EventResizeHandler eventResizeHandler;

	@Autowired
	private EventMoveHandler eventMoveHandler;

	@Autowired
	private RangeSelectHandler rangeSelectHandler;

	@Autowired
	private EventClickHandler eventClickHandler;

	@Autowired
	private Handler demoRightClickActionHandler;

	@Autowired
	private ApplicationContext ctx;

	CalendarFactoryBean(String id)
	{
		this(new DefaultIdCallback(id));
	}

	CalendarFactoryBean(IdCallback callback)
	{
		this.callback = callback;
	}

	@SuppressWarnings("unused")
	private CalendarFactoryBean()
	{
		this((IdCallback) null);
	}

	@Override
	public Calendar getObject() throws Exception
	{
		Calendar calendar = new Calendar();
		calendar.setEventProvider((CalendarEventProvider) ctx.getBean(
				"calendarEventProvider", callback));
		calendar.setHandler(rangeSelectHandler);
		calendar.setHandler(eventClickHandler);
		calendar.addActionHandler(demoRightClickActionHandler);
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