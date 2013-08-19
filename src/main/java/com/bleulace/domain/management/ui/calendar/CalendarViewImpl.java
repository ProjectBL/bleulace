package com.bleulace.domain.management.ui.calendar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContextFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

@Configurable
class CalendarViewImpl extends CustomComponent implements CalendarView, View
{
	private List<CalendarViewListener> listeners = new LinkedList<CalendarViewListener>();

	@Autowired
	private CalendarViewContextFactory contextFactory;

	private CalendarViewContext context;

	private final Calendar calendar;

	private Map<ScheduleStatus, Panel> timeslots = new HashMap<ScheduleStatus, Panel>();

	CalendarViewImpl()
	{
		CalendarPresenter presenter = new CalendarPresenter();
		addViewListener(presenter);

		calendar = new Calendar();
		calendar.setHandler((BackwardHandler) presenter);
		calendar.setHandler((ForwardHandler) presenter);
		calendar.setHandler((RangeSelectHandler) presenter);
		calendar.setHandler((DateClickHandler) presenter);

		setCompositionRoot(new HorizontalLayout(calendar, initializeTimeSlots()));
	}

	private VerticalLayout initializeTimeSlots()
	{
		VerticalLayout layout = new VerticalLayout();
		for (ScheduleStatus key : ScheduleStatus.values())
		{
			Panel panel = new Panel(new VerticalLayout());
			timeslots.put(key, panel);
			layout.addComponent(panel);
		}
		return layout;
	}

	@Override
	public CalendarViewContext getContext()
	{
		return context;
	}

	@Override
	public void setTitle(String title)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addTimeSlot(LocalTime time, ScheduleStatus status)
	{
		ComponentContainer layout = (ComponentContainer) timeslots.get(status)
				.getContent();
		layout.addComponent(new TimeSlot(time, status));
	}

	@Override
	public void removeTimeSlot(LocalTime time)
	{
		for (Panel panel : timeslots.values())
		{
			ComponentContainer layout = (ComponentContainer) panel.getContent();
			for (Component c : layout)
			{
				TimeSlot t = (TimeSlot) c;
				if (t.getTime().equals(time))
				{
					layout.removeComponent(c);
				}
			}
		}
	}

	@Override
	public void clearTimeslots()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCalendarType(CalendarType type)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addViewListener(CalendarViewListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		String ownerId = event.getParameters();
		String viewerId = SecurityUtils.getSubject().getId();
		context = contextFactory.make(ownerId, viewerId);
		calendar.setEventProvider(context.getEventProvider());
	}
}
