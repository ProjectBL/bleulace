package com.bleulace.web.demo.calendar.handler;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.DefaultIdCallback;
import com.bleulace.utils.IdCallback;
import com.bleulace.web.SystemUser;
import com.bleulace.web.demo.calendar.CalendarEventAdapter;
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;

@Lazy
@Scope("prototype")
@Component("calendarEventProvider")
class DemoCalendarEventProvider extends BasicEventProvider implements
		CachingEventProvider
{
	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private SystemUser user;

	private IdCallback callback;

	DemoCalendarEventProvider(String id)
	{
		this.callback = new DefaultIdCallback(id);
	}

	DemoCalendarEventProvider(IdCallback callback)
	{
		this.callback = callback;
	}

	private DemoCalendarEventProvider()
	{
	}

	@Override
	@RequiresUser
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		for (PersistentEvent event : eventDAO.findEvents(startDate, endDate,
				callback.evaluate()))
		{
			CalendarEventAdapter adapted = (CalendarEventAdapter) ctx.getBean(
					"calendarAdapter", event);
			if (!eventList.contains(adapted))
			{
				adapted.addEventChangeListener(this);
				eventList.add(adapted);
			}
		}
		return super.getEvents(startDate, endDate);
	}

	@Override
	public void addEvent(CalendarEvent event)
	{
		CalendarEventAdapter adapter = (CalendarEventAdapter) event;
		PersistentEvent source = adapter.getSource();
		adapter.setSource(eventDAO.save(source));
		adapter.addEventChangeListener(this);
		adapter.getAssignments().add(
				new ManagementAssignment(accountDAO.findOne(user.getId()),
						ManagementLevel.OWN));
		adapter.getInvitees().add(
				new EventInvitee(accountDAO.findOne(user.getId()),
						RsvpStatus.ACCEPTED));
		eventList.add(event);
	}

	@Override
	public void removeEvent(CalendarEvent event)
	{
		CalendarEventAdapter adapted = (CalendarEventAdapter) event;
		adapted.removeEventChangeListener(this);
		eventDAO.delete(adapted.getSource());
		super.removeEvent(adapted);
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

	@Override
	public void eventChange(EventChangeEvent changeEvent)
	{
		CalendarEventAdapter adapted = (CalendarEventAdapter) changeEvent
				.getCalendarEvent();
		PersistentEvent event = eventDAO.save(adapted.getSource());
		adapted.setSource(event);
		adapted.setStyleName(getStyleName(adapted));
		super.eventChange(changeEvent);
	}

	private String getStyleName(CalendarEventAdapter event)
	{
		RsvpStatus status = eventDAO.findStatus(event.getSource().getId(),
				callback.evaluate());
		return status == null ? null : status.getStyleName();
	}
}