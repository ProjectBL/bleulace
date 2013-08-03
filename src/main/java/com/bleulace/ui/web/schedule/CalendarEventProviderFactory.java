package com.bleulace.ui.web.schedule;

import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
abstract class CalendarEventProviderFactory
{
	private CalendarEventProviderFactory()
	{
	}

	static CalendarEventProvider make(String viewerId, String targetId)
	{
		return viewerId.equals(targetId) ? new SelfCalendarEventProvider(
				viewerId) : new OtherCalendarEventProvider(viewerId, targetId);
	}
}