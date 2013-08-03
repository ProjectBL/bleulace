package com.bleulace.ui.web.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
class SelfCalendarEventProvider implements CalendarEventProvider
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 125075528882233978L;

	private final String accountId;

	SelfCalendarEventProvider(String accountId)
	{
		Assert.notNull(accountId);
		this.accountId = accountId;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		// TODO Auto-generated method stub
		return null;
	}
}