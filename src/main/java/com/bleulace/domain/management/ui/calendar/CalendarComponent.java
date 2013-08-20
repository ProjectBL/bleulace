package com.bleulace.domain.management.ui.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Range;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.vaadin.navigator.Navigator.EmptyView;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

class CalendarComponent extends CustomComponent implements
		SelectedTabChangeListener
{
	private final Calendar calendar;

	private final Map<Tab, CalendarType> tabs = new HashMap<Tab, CalendarType>();

	CalendarComponent(CalendarViewContext ctx)
	{
		calendar = makeCalendar(ctx);

		setCompositionRoot(makeTabs());
	}

	private TabSheet makeTabs()
	{
		TabSheet tabSheet = new TabSheet();

		for (CalendarType type : CalendarType.values())
		{
			System.out.println(type);
			tabs.put(tabSheet.addTab(new CalendarContainer(), type.toString()),
					type);
		}

		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSelectedTab(0);
		return tabSheet;
	}

	private static Calendar makeCalendar(CalendarViewContext ctx)
	{
		Calendar calendar = new Calendar(ctx.getEventProvider());
		calendar.setTimeZone(ctx.getTimeZone());
		calendar.setHandler((EventMoveHandler) ctx.getHandlers());
		calendar.setHandler((EventResizeHandler) ctx.getHandlers());
		calendar.setHandler((DateClickHandler) ctx.getHandlers());
		calendar.setHandler((RangeSelectHandler) ctx.getHandlers());
		calendar.setSizeFull();
		return calendar;
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event)
	{
		for (Tab tab : tabs.keySet())
		{
			((CalendarContainer) tab.getComponent()).setActive(false);
		}

		TabSheet tabSheet = event.getTabSheet();
		CalendarContainer container = (CalendarContainer) tabSheet
				.getSelectedTab();
		container.setActive(true);

		Tab selected = tabSheet.getTab(container);
		Range<Date> dates = tabs.get(selected).convert(new Date());
		calendar.setStartDate(dates.getMinimum());
		calendar.setEndDate(dates.getMaximum());
	}

	class CalendarContainer extends CustomComponent
	{
		void setActive(boolean active)
		{
			setCompositionRoot(active ? calendar : new EmptyView());
		}
	}
}