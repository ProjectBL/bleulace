package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.web.demo.calendar.span.CalendarSpan;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

@Lazy
@Scope("prototype")
@Component("calendarTabsheet")
class CalendarTabsFactoryBean implements FactoryBean<TabSheet>
{
	private final Calendar calendar;

	CalendarTabsFactoryBean(Calendar calendar)
	{
		this.calendar = calendar;
	}

	@SuppressWarnings("unused")
	private CalendarTabsFactoryBean()
	{
		this(new Calendar());
	}

	@Override
	public TabSheet getObject() throws Exception
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

		calendar.setHandler(new DateClickHandler()
		{
			@Override
			public void dateClick(DateClickEvent event)
			{
				bean.setSelectedTab(0);
				new BasicDateClickHandler().dateClick(event);
			}
		});
		return bean;
	}

	@Override
	public Class<?> getObjectType()
	{
		return TabSheet.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}