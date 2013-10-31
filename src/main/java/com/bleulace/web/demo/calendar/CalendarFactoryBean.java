package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.FactoryBean;

import com.bleulace.web.demo.calendar.handler.CachingEventProvider;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Calendar;

class CalendarFactoryBean extends Calendar implements FactoryBean<Calendar>
{
	@Override
	public Calendar getObject() throws Exception
	{
		return this;
	}

	@Override
	public Class<?> getObjectType()
	{
		return Calendar.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

	@Subscribe
	public void subscribe(ViewTargetChangedEvent event)
	{
		if (getEventProvider() instanceof CachingEventProvider)
		{
			((CachingEventProvider) getEventProvider()).clearCache();
		}
		markAsDirty();
	}
}
