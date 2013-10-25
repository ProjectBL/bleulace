package com.bleulace.web.demo.calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bleulace.utils.SystemProfiles;
import com.bleulace.web.demo.timebox.EventBean;
import com.bleulace.web.demo.timebox.TimeBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;

@Configuration
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class CalendarLeftClickConfig
{
	@Bean
	public RangeSelectHandler rangeSelectHandler()
	{
		return new RangeSelectHandler()
		{
			@Override
			public void rangeSelect(RangeSelectEvent event)
			{
				EventBean bean = new EventBean();
				bean.setStart(event.getStart());
				bean.setEnd(event.getEnd());
				UI.getCurrent().addWindow(
						new TimeBox(bean, (BasicEventProvider) event
								.getComponent().getEventProvider()));
			}
		};
	}

	@Bean
	public EventClickHandler eventClickHandler()
	{
		return new EventClickHandler()
		{
			@Override
			public void eventClick(EventClick event)
			{
				EventBean bean = (EventBean) event.getCalendarEvent();
				Window w = new TimeBox(bean, (BasicEventProvider) event
						.getComponent().getEventProvider());
				UI.getCurrent().addWindow(w);
				w.focus();
			}
		};
	}
}