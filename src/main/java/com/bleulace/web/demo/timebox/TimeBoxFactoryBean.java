package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.demo.calendar.CalendarEventAdapter;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Window;

@Lazy
@Component("timeBox")
@Scope("prototype")
class TimeBoxFactoryBean implements FactoryBean<Window>
{
	private final CalendarEventAdapter event;
	private final Calendar calendar;

	TimeBoxFactoryBean(Calendar calendar, CalendarEventAdapter event)
	{
		this.calendar = calendar;
		this.event = event;
	}

	@SuppressWarnings("unused")
	private TimeBoxFactoryBean()
	{
		this(new Calendar(), (CalendarEventAdapter) SpringApplicationContext
				.getBean("calendarAdapter", new PersistentEvent()));
	}

	@Override
	public Window getObject() throws Exception
	{
		TimeBoxPresenter presenter = new TimeBoxPresenter(event, calendar);
		TimeBox timeBox = new TimeBox(presenter);
		presenter.setView(timeBox);
		return timeBox;
	}

	@Override
	public Class<?> getObjectType()
	{
		return Window.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}
}
