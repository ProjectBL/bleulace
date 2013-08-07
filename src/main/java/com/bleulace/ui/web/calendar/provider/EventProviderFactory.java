package com.bleulace.ui.web.calendar.provider;

import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public abstract class EventProviderFactory
{
	private EventProviderFactory()
	{
	}

	public static CalendarEventProvider make(String ownerId, String viewerId)
	{
		return new PersistentCalendarEventProvider(ownerId, viewerId);
	}
}
