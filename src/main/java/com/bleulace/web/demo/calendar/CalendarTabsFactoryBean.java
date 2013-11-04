package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.web.demo.calendar.span.CalendarSpan;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

@Lazy
@Scope("prototype")
@Component("calendarTabs")
class CalendarTabsFactoryBean implements FactoryBean<TabSheet>
{
	private final Calendar calendar;

	CalendarTabsFactoryBean(Calendar calendar)
	{
		this.calendar = calendar;
	}

	private CalendarTabsFactoryBean()
	{
		this(new Calendar());
	}

	@Override
	public TabSheet getObject() throws Exception
	{
		final TabSheet bean = new TabSheet();

		calendar.setSizeFull();

		for (final CalendarSpan span : CalendarSpan.values())
		{
			Layout l = new VerticalLayout();
			bean.addTab(l, span.toString());
			l.setSizeFull();
		}

		bean.addSelectedTabChangeListener(new SelectedTabChangeListener()
		{
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event)
			{
				int pos = bean.getTabPosition(bean.getTab(bean.getSelectedTab()));
				int length = CalendarSpan.values().length;
				if (pos > length - 1)
				{
					return;
				}
				for (int i = 0; i < CalendarSpan.values().length; i++)
				{
					Layout l = (Layout) bean.getTab(i).getComponent();
					for (com.vaadin.ui.Component c : l)
					{
						if (c.equals(calendar))
						{
							l.removeComponent(calendar);
							((ComponentContainer) event.getTabSheet()
									.getSelectedTab()).addComponent(calendar);
						}
						if (bean.getTabPosition(bean.getTab(bean
								.getSelectedTab())) == i)
						{
							CalendarSpan.values()[i].resize(calendar);
						}
					}
				}
				((Layout) bean.getSelectedTab()).addComponent(calendar);
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