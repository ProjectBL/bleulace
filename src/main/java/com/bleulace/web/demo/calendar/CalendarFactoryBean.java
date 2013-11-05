package com.bleulace.web.demo.calendar;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.utils.DefaultIdCallback;
import com.bleulace.utils.IdCallback;
import com.bleulace.web.SystemUser;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

@Lazy
@Scope("prototype")
@Component("calendar")
class CalendarFactoryBean implements FactoryBean<Calendar>
{
	private IdCallback callback;

	@Autowired
	private RangeSelectHandler rangeSelectHandler;

	@Autowired
	private EventClickHandler eventClickHandler;

	@Autowired
	private Handler demoRightClickActionHandler;

	@Autowired
	private ApplicationContext ctx;

	CalendarFactoryBean()
	{
	}

	CalendarFactoryBean(String id)
	{
		this(new DefaultIdCallback(id));
	}

	CalendarFactoryBean(IdCallback callback)
	{
		this.callback = callback;
	}

	@PostConstruct
	protected void init()
	{
		if (callback == null)
		{
			callback = ctx.getBean(SystemUser.class);
		}
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