package com.bleulace.domain.management.ui.calendar.component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.vaadin.navigator.Navigator.EmptyView;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class CalendarComponentImpl extends CustomComponent implements
		SelectedTabChangeListener, CalendarComponent
{
	private final List<CalendarComponentListener> listeners = new LinkedList<CalendarComponentListener>();

	@Autowired
	private CalendarComponentHandlers handlers;

	private final Map<Tab, CalendarType> tabs = new HashMap<Tab, CalendarType>();

	private final Calendar calendar = makeCalender();

	private final TabSheet content = makeTabs();

	CalendarComponentImpl()
	{
		setCompositionRoot(content);
	}

	@PostConstruct
	protected void init()
	{
		handlers.setComponentListeners(listeners);
		setHandlers(handlers);
	}

	@Override
	public void initialize(CalendarViewContext ctx)
	{
		calendar.setEventProvider(ctx.getEventProvider());
		calendar.setTimeZone(ctx.getTimeZone());
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

		Range<Date> oldDates = getRange();
		Range<Date> newDates = tabs.get(selected).convert(new Date());

		calendar.setStartDate(newDates.getMinimum());
		calendar.setEndDate(newDates.getMaximum());

		handlers.datesChanged(oldDates, newDates);
	}

	@Override
	public Range<Date> getRange()
	{
		return calendar.getRange();
	}

	@Override
	public void addListener(CalendarComponentListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeListener(CalendarComponentListener listener)
	{
		listeners.remove(listener);
	}

	private Calendar makeCalender()
	{
		Calendar calendar = new Calendar();
		calendar.setSizeFull();
		Range<Date> range = CalendarType.WEEK.convert(new Date());
		calendar.setStartDate(range.getMinimum());
		calendar.setEndDate(range.getMaximum());
		return calendar;
	}

	private void setHandlers(CalendarComponentHandlers handlers)
	{
		calendar.setHandler((ForwardHandler) handlers);
		calendar.setHandler((BackwardHandler) handlers);
		calendar.setHandler((DateClickHandler) handlers);
		calendar.setHandler((WeekClickHandler) handlers);
		calendar.setHandler((EventMoveHandler) handlers);
		calendar.setHandler((EventClickHandler) handlers);
		calendar.setHandler((EventResizeHandler) handlers);
		calendar.setHandler((RangeSelectHandler) handlers);
	}

	private TabSheet makeTabs()
	{
		TabSheet tabSheet = new TabSheet();

		for (CalendarType type : CalendarType.values())
		{
			CalendarContainer container = new CalendarContainer();
			container.setActive(type.equals(CalendarType.DAY));
			tabs.put(tabSheet.addTab(container, type.toString()), type);
		}

		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSelectedTab(CalendarType.DAY.ordinal());
		return tabSheet;
	}

	private class CalendarContainer extends CustomComponent
	{
		void setActive(boolean active)
		{
			setCompositionRoot(active ? calendar : new EmptyView());
		}
	}
}