package com.bleulace.web.demo.calendar.handler;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.demo.calendar.appearance.StyleNameCallback;
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

@Scope("prototype")
@Component
class DemoCalendarEventProvider extends BasicEventProvider implements
		CachingEventProvider
{
	private final String id;

	private final StyleNameCallback styleNameCallback;

	DemoCalendarEventProvider(String id)
	{
		this(id, (StyleNameCallback) SpringApplicationContext.getBean(
				"defaultStyleNameCallback", id));
	}

	DemoCalendarEventProvider(String id, StyleNameCallback styleNameCallback)
	{
		this.id = id;
		this.styleNameCallback = styleNameCallback;
	}

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private ApplicationContext ctx;

	@Override
	@RequiresUser
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		for (PersistentEvent event : eventDAO
				.findEvents(startDate, endDate, id))
		{
			if (!eventList.contains(event))
			{
				eventList.add(event);
			}
		}
		return super.getEvents(startDate, endDate);
	}

	@Override
	public void addEvent(CalendarEvent event)
	{
		if (event instanceof PersistentEvent)
		{
			PersistentEvent persistentEvent = (PersistentEvent) event;
			persistentEvent.addEventChangeListener(this);

			persistentEvent.setRsvpStatus(id, RsvpStatus.ACCEPTED);
			persistentEvent.setManagementLevel(id, ManagementLevel.OWN);

			persistentEvent.setCallback(styleNameCallback);

			event = eventDAO.save(persistentEvent);
		}
		super.addEvent(event);
	}

	@Override
	public void removeEvent(CalendarEvent event)
	{
		if (event instanceof PersistentEvent)
		{
			PersistentEvent persistentEvent = (PersistentEvent) event;
			persistentEvent.removeEventChangeListener(this);
			eventDAO.delete(persistentEvent);
		}
		super.removeEvent(event);
	}

	@Override
	public boolean containsRange(CalendarDateRange range)
	{
		return super.getEvents(range.getStart(), range.getEnd()).size() > 0;
	}

	@Override
	public void clearCache()
	{
		eventList.clear();
	}
}