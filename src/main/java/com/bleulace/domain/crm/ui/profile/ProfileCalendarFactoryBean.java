package com.bleulace.domain.crm.ui.profile;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

@UIComponent("profileCalendar")
class ProfileCalendarFactoryBean implements FactoryBean<Calendar>,
		EventClickHandler, RangeSelectHandler
{
	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	private ProfileCalendarFactoryBean()
	{
	}

	@Override
	public Calendar getObject() throws Exception
	{
		Calendar calendar = new Calendar();

		calendar.setImmediate(true);
		calendar.setHandler((EventClickHandler) this);
		calendar.setHandler((RangeSelectHandler) this);

		Range<Date> range = CalendarType.DAY.convert(new Date());
		calendar.setStartDate(range.getMinimum());
		calendar.setEndDate(range.getMaximum());

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
		return true;
	}

	@Override
	public void eventClick(EventClick event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event));
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event));
	}
}
