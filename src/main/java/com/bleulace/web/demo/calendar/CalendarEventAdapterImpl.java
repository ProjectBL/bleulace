package com.bleulace.web.demo.calendar;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.ui.components.calendar.event.BasicEvent;

@Lazy
@Component("calendarAdapter")
@Scope("prototype")
class CalendarEventAdapterImpl extends BasicEvent implements
		CalendarEventAdapter
{
	private PersistentEvent source;

	private String styleName;

	CalendarEventAdapterImpl(CalendarEventAdapter adapter)
	{
		this(adapter.getSource());
	}

	CalendarEventAdapterImpl(PersistentEvent source)
	{
		this.source = source;
	}

	CalendarEventAdapterImpl()
	{
		this(new PersistentEvent());
	}

	@Override
	public void setCaption(String caption)
	{
		source.setTitle(caption);
		fireEventChange();
	}

	@Override
	public void setDescription(String description)
	{
		source.setLocation(description);
		fireEventChange();
	}

	@Override
	public void setEnd(Date end)
	{
		source.setEnd(end);
		fireEventChange();
	}

	@Override
	public void setStart(Date start)
	{
		source.setStart(start);
		fireEventChange();
	}

	@Override
	public void setStyleName(String styleName)
	{
		this.styleName = styleName;
	}

	@Override
	public void setAllDay(boolean isAllDay)
	{
		fireEventChange();
	}

	@Override
	public Date getStart()
	{
		return source.getStart();
	}

	@Override
	public Date getEnd()
	{
		return source.getEnd();
	}

	@Override
	public String getCaption()
	{
		return source.getTitle();
	}

	@Override
	public String getDescription()
	{
		return source.getLocation();
	}

	@Override
	public String getStyleName()
	{
		return styleName;
	}

	@Override
	public boolean isAllDay()
	{
		return false;
	}

	@Override
	public PersistentEvent getSource()
	{
		return source;
	}

	@Override
	public void setSource(PersistentEvent source)
	{
		Assert.notNull(source);
		this.source = source;
	}

	@Override
	public List<EventInvitee> getInvitees()
	{
		return source.getInvitees();
	}

	@Override
	public List<ManagementAssignment> getAssignments()
	{
		return source.getAssignments();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof CalendarEventAdapterImpl)
		{
			return source.getId().equals(
					((CalendarEventAdapterImpl) obj).getSource().getId());
		}
		return false;
	}

	@Override
	protected void fireEventChange()
	{
		super.fireEventChange();
	}
}