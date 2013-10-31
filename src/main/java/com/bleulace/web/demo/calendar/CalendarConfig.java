package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.web.annotation.WebProfile;
import com.bleulace.web.demo.calendar.span.CalendarSpan;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
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
	public CalendarFactoryBean calendar(CalendarEventProvider eventProvider,
			EventClickHandler eventClickHandler,
			RangeSelectHandler rangeSelectHandler,
			DateClickHandler dateClickHandler,
			EventMoveHandler eventMoveHandler,
			EventResizeHandler eventResizeHandler,
			@Qualifier("demoEventRightClickHandler") Handler actionHandler)
	{
		CalendarFactoryBean bean = new CalendarFactoryBean();
		bean.setEventProvider(eventProvider);
		bean.setHandler(eventClickHandler);
		bean.setHandler(rangeSelectHandler);
		bean.setHandler(dateClickHandler);
		bean.setHandler(eventMoveHandler);
		bean.setHandler(eventResizeHandler);
		bean.addActionHandler(actionHandler);
		bean.setImmediate(true);
		return bean;
	}

	@Bean
	@Scope("ui")
	public TabSheet tabSheet(final Calendar calendar)
	{
		final TabSheet bean = new TabSheet();
		for (final CalendarSpan span : CalendarSpan.values())
		{
			bean.addTab(new AbsoluteLayout(), span.toString());
		}
		bean.addSelectedTabChangeListener(new SelectedTabChangeListener()
		{
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event)
			{
				int i = bean.getTabPosition(bean.getTab(bean.getSelectedTab()));
				CalendarSpan.values()[i].resize(calendar);
			}
		});
		bean.setSelectedTab(CalendarSpan.WEEK.ordinal());
		return bean;
	}
}
