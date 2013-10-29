package com.bleulace.web.demo.calendar.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.ui.components.calendar.event.CalendarEditableEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeNotifier;

@Component
class DemoCalendarEventProvider implements CalendarEditableEventProvider,
		EventSetChangeNotifier, CalendarEvent.EventChangeListener
{
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private EventDAO eventDAO;

	private List<EventSetChangeListener> listeners = new ArrayList<EventSetChangeListener>();

	@Override
	@RequiresUser
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		String targetId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("targetId");
		if (targetId == null || !accountDAO.exists(targetId))
		{
			return events;
		}

		for (PersistentEvent event : eventDAO.findEvents(startDate, endDate,
				targetId))
		{
			event.addEventChangeListener(this);
			events.add(event);
		}
		return events;
	}

	@Override
	public void addEvent(CalendarEvent calendarEvent)
	{
		fireEventSetChange();
	}

	@Override
	public void removeEvent(CalendarEvent calendarEvent)
	{
		PersistentEvent event = (PersistentEvent) calendarEvent;
		eventDAO.delete(event);
	}

	@Override
	public void eventChange(EventChangeEvent eventChangeEvent)
	{
		fireEventSetChange();
	}

	@Override
	public void addEventSetChangeListener(EventSetChangeListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeEventSetChangeListener(EventSetChangeListener listener)
	{
		listeners.remove(listener);
	}

	protected void fireEventSetChange()
	{
		EventSetChangeEvent event = new EventSetChangeEvent(this);
		for (EventSetChangeListener listener : listeners)
		{
			listener.eventSetChange(event);
		}
	}
}