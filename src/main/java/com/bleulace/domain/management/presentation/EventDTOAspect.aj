package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.bleulace.jpa.DateWindow;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;

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
	void around(EventDTO dto) :
		call(public void EventDTO+.set*(*)) 
		&& target(dto) 
	{
		EventChangeEvent event = new EventChangeEvent(dto);
		for (EventChangeListener listener : dto.listeners)
		{
			listener.eventChange(event);
		}
		proceed(dto);
	}
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
}