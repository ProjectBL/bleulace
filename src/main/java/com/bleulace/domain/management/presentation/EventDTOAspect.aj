package com.bleulace.domain.management.presentation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.util.Assert;

import com.bleulace.jpa.DateWindow;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

aspect EventDTOAspect
{
	@NotNull
	private String EventDTO.caption = "";

	@NotNull
	private Date EventDTO.start;

	@NotNull
	private Date EventDTO.end;

	@NotNull
	private boolean EventDTO.allDay = false;

	private String EventDTO.styleName = null;

	private transient List<EventChangeListener> EventDTO.listeners = new ArrayList<EventChangeListener>();

	// ---------------------------------------------------
	void around(EventDTO dto, Object newValue) :
		call(public void EditableCalendarEvent.set*(*)) 
		&& this(dto) 
		&& args(newValue) 
	{
		Assert.notNull(newValue);
		String methodName = thisJoinPoint.getSignature().getName()
				.replace("set", "get");
		Object oldValue = null;
		try
		{
			Method m = MethodUtils.getAccessibleMethod(dto.getClass(),
					methodName);
			oldValue = m == null ? null : m.invoke(dto);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			proceed(dto, newValue);
			if (oldValue != newValue || !oldValue.equals(newValue))
			{
				dto.fireEventChange();
			}
		}
	}

	// PUBLIC ACCESSORS AND MUTATORS
	// ---------------------------------------------------

	public Date EventDTO.getStart()
	{
		return start;
	}

	public void EventDTO.setStart(Date start)
	{
		this.start = start;
	}

	public Date EventDTO.getEnd()
	{
		return end;
	}

	public void EventDTO.setEnd(Date end)
	{
		this.end = end;
	}

	public void EventDTO.setWindow(DateWindow window)
	{
		this.start = window.getStart();
		this.end = window.getEnd();
	}

	public boolean EventDTO.isAllDay()
	{
		return allDay;
	}

	public void EventDTO.setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}

	public Date EventDTOImpl.getStart()
	{
		return start;
	}

	public void EventDTOImpl.setStart(Date start)
	{
		this.start = start;
	}

	public String EventDTO.getStyleName()
	{
		return styleName;
	}

	public void EventDTO.setStyleName(String styleName)
	{
		this.styleName = styleName;
	}

	public void EventDTO.addEventChangeListener(EventChangeListener listener)
	{
		listeners.add(listener);
	}

	public void EventDTO.removeEventChangeListener(EventChangeListener listener)
	{
		listeners.remove(listener);
	}

	private void EventDTO.fireEventChange()
	{
		EventChangeEvent event = new EventChangeEvent(this);
		for (EventChangeListener listener : listeners)
		{
			listener.eventChange(event);
		}
	}
}