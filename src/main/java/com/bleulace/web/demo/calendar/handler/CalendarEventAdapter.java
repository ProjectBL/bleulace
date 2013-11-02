package com.bleulace.web.demo.calendar.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.ui.components.calendar.event.BasicEvent;

@Lazy
@Scope("prototype")
@Component
class CalendarEventAdapter extends BasicEvent implements AdaptedCalendarEvent
{
	@Autowired
	private transient EventDAO eventDAO;

	private PersistentEvent event;

	private CalendarEventAdapter(PersistentEvent event)
	{
		this.event = event;
	}

	private CalendarEventAdapter()
	{
		this(null);
	}

	@Override
	public String getId()
	{
		return event.getId();
	}

	@Override
	public Date getStart()
	{
		return event.getStart();
	}

	@Override
	public void setStart(Date start)
	{
		event.setStart(start);
		fireEventChange();
	}

	@Override
	public Date getEnd()
	{
		return event.getEnd();
	}

	@Override
	public void setEnd(Date end)
	{
		event.setEnd(end);
		fireEventChange();
	}

	@Override
	public String getCaption()
	{
		return event.getTitle();
	}

	@Override
	public void setCaption(String caption)
	{
		event.setTitle(caption);
		fireEventChange();
	}

	@Override
	public String getDescription()
	{
		return event.getLocation();
	}

	@Override
	public void setDescription(String description)
	{
		event.setLocation(description);
		fireEventChange();
	}

	@Override
	public boolean isAllDay()
	{
		return false;
	}

	@Override
	public String getStyleName()
	{
		String id = SpringApplicationContext.getUser().getId();
		RsvpStatus status = event.getRsvpStatus(id);
		return status == null ? null : status.getStyleName();
	}

	@Override
	public RsvpStatus getRsvpStatus(String accountId)
	{
		return event.getRsvpStatus(accountId);
	}

	@Override
	public void setRsvpStatus(String accountId, RsvpStatus status)
	{
		event.setRsvpStatus(accountId, status);
		fireEventChange();
	}

	@Override
	public ManagementLevel getManagementLevel(String accountId)
	{
		return event.getManagementLevel(accountId);
	}

	@Override
	public void setManagementLevel(String accountId, ManagementLevel level)
	{
		event.setManagementLevel(accountId, level);
		fireEventChange();
	}

	@Override
	public boolean equals(Object obj)
	{

		if (null == obj)
		{
			return false;
		}

		if (this == obj)
		{
			return true;
		}

		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		CalendarEventAdapter that = (CalendarEventAdapter) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public AdaptedCalendarEvent save()
	{
		event = eventDAO.save(event);
		return this;
	}

	@Override
	public void delete()
	{
		eventDAO.delete(event);
	}
}