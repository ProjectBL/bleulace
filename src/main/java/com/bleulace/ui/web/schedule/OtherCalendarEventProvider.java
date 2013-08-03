package com.bleulace.ui.web.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

/**
 * Event Provider for use when viewing ANOTHER account's calendar
 * 
 * @author Arleigh Dickerson
 * 
 */
class OtherCalendarEventProvider implements CalendarEventProvider
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3901627135317542321L;

	private final String viewerId;
	private final String targetId;

	public OtherCalendarEventProvider(String viewerId, String targetId)
	{
		Assert.noNullElements(new Object[] { viewerId, targetId });
		this.targetId = targetId;
		this.viewerId = viewerId;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
