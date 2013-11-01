package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Window;

@Component("timeBox")
@Scope("prototype")
class TimeBoxFactoryBean implements FactoryBean<Window>
{
	private final PersistentEvent event;
	private final Calendar calendar;

	TimeBoxFactoryBean(Calendar calendar, PersistentEvent event)
	{
		this.calendar = calendar;
		this.event = event;
	}

	private TimeBoxFactoryBean()
	{
		this(new Calendar(), new PersistentEvent());
	}

	@Override
	public Window getObject() throws Exception
	{
		TimeBoxPresenter presenter = new TimeBoxPresenter(calendar);
		TimeBox timeBox = new TimeBox(event, presenter);
		presenter.setTimeBox(timeBox);
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
