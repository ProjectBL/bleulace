package com.bleulace.domain.management.ui.calendar.view;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.vaadin.navigator.Navigator.EmptyView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;

@Configurable
class CalendarViewImpl extends CustomComponent implements CalendarView, View
{
	private final List<CalendarViewListener> listeners = new LinkedList<CalendarViewListener>();

	private final CalendarHandlers handlers = new CalendarHandlers(listeners);

	private final EventDTOProvider provider = new EventDTOProvider();

	private final CalendarContainer container = new CalendarContainer();

	private final TabSheet tabs = makeTabs();

	private Calendar calendar;

	CalendarViewImpl()
	{
		VerticalLayout layout = new VerticalLayout(tabs, container);
		setCompositionRoot(layout);
	}

	@Override
	public void showModal(EventDTO dto, EventDTOCommandCallback<?> callback)
	{
		getUI().addWindow(new CalendarModal(this, dto, callback));
	}

	@Override
	public void setVisibleDates(Date start, Date end)
	{
		calendar.setStartDate(start);
		calendar.setEndDate(end);
	}

	@Override
	public void addViewListener(CalendarViewListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void refreshCalendar()
	{
		container.refresh();
	}

	@Override
	@RequiresAuthentication
	public void enter(ViewChangeEvent event)
	{
		String viewerId = SecurityUtils.getSubject().getId();
		String ownerId = event.getParameters();
		provider.setContext(ownerId, viewerId);
	}

	private Calendar makeCalendar()
	{
		Calendar calendar = new Calendar(provider);
		calendar.setHandler((BackwardHandler) handlers);
		calendar.setHandler((ForwardHandler) handlers);
		calendar.setHandler((WeekClickHandler) handlers);
		calendar.setHandler((DateClickHandler) handlers);
		calendar.setHandler((EventMoveHandler) handlers);
		calendar.setHandler((EventResizeHandler) handlers);
		calendar.setHandler((EventClickHandler) handlers);
		calendar.setHandler((RangeSelectHandler) handlers);
		if (this.calendar != null)
		{
			calendar.setStartDate(this.calendar.getStartDate());
			calendar.setEndDate(this.calendar.getEndDate());
		}
		return calendar;
	}

	private TabSheet makeTabs()
	{
		TabSheet tabSheet = new TabSheet();
		for (CalendarType type : CalendarType.values())
		{
			Tab tab = tabSheet.addTab(new EmptyView(), type.toString());
			tabSheet.addSelectedTabChangeListener(new CalendarTypeTabListener(
					tab, type));
		}
		return tabSheet;
	}

	private class CalendarTypeTabListener implements SelectedTabChangeListener
	{
		private final Tab tab;
		private final CalendarType type;

		CalendarTypeTabListener(Tab tab, CalendarType type)
		{
			this.tab = tab;
			this.type = type;
		}

		@Override
		public void selectedTabChange(SelectedTabChangeEvent event)
		{
			TabSheet tabSheet = event.getTabSheet();
			Tab tab = tabSheet.getTab(tabSheet.getSelectedTab());
			if (this.tab.equals(tab))
			{
				for (CalendarViewListener l : listeners)
				{
					l.calendarTypeChanged(type);
				}
			}
		}
	}

	private class CalendarContainer extends CustomComponent
	{
		CalendarContainer()
		{
			refresh();
		}

		void refresh()
		{
			calendar = makeCalendar();
			setCompositionRoot(calendar);
		}
	}
}