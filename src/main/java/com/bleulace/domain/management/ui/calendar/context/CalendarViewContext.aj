package com.bleulace.domain.management.ui.calendar.context;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeListener;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeNotifier;

public interface CalendarViewContext extends EventSetChangeNotifier,
		Serializable
{
	public String getOwnerId();

	public String getViewerId();

	public void decorate(EventDTO dto);

	public TimeZone getTimeZone();

	public CalendarEventProvider getEventProvider();

	static aspect Impl
	{
		private final List<EventSetChangeListener> CalendarViewContext.listeners = new LinkedList<EventSetChangeListener>();

		public void CalendarViewContext.addEventSetChangeListener(
				EventSetChangeListener listener)
		{
			listeners.add(listener);
		}

		public void CalendarViewContext.removeEventSetChangeListener(
				EventSetChangeListener listener)
		{
			listeners.remove(listener);
		}

		public void CalendarViewContext.fireEventChange()
		{
			EventSetChangeEvent changeEvent = new EventSetChangeEvent(
					this.getEventProvider());
			for (EventSetChangeListener listener : listeners)
			{
				listener.eventSetChange(changeEvent);
			}
		}
	}
}