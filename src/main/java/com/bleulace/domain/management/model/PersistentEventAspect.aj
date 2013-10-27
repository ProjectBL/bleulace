package com.bleulace.domain.management.model;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;

aspect PersistentEventAspect
{
	declare parents : PersistentEvent implements EventChangeNotifier;

	private transient List<EventChangeListener> PersistentEvent.listeners = new ArrayList<EventChangeListener>();

	after(PersistentEvent calendarEvent) returning() : 
		execution(public void PersistentEvent.set*(*)) && 
		this(calendarEvent){

		EventChangeEvent event = new EventChangeEvent(calendarEvent);

		for (EventChangeListener listener : calendarEvent.listeners)
		{
			listener.eventChange(event);
		}
	}

	public void PersistentEvent.addEventChangeListener(
			EventChangeListener listener)
	{
		listeners.add(listener);
	}

	public void PersistentEvent.removeEventChangeListener(
			EventChangeListener listener)
	{
		listeners.remove(listener);
	}
}
