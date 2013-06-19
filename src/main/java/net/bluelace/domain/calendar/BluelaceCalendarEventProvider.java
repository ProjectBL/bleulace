package net.bluelace.domain.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.bluelace.domain.account.Account;

import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public class BluelaceCalendarEventProvider implements CalendarEventProvider
{
	private static final long serialVersionUID = -1226728585476852786L;

	private final Account account;

	public BluelaceCalendarEventProvider(Account accout)
	{
		this.account = accout;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		return new ArrayList<CalendarEvent>();
	}
}