package com.bleulace.ui.web.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.QCalendarEntryParticipant;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public class AccountCalendarEventProvider implements CalendarEventProvider
{
	private static final long serialVersionUID = 3734611284677551266L;

	private final Account account;

	public AccountCalendarEventProvider(Account account)
	{
		Assert.isTrue(account != null && !account.isNew());
		this.account = account;
	}

	@Transactional
	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		QCalendarEntryParticipant p = QCalendarEntryParticipant.calendarEntryParticipant;
		return new ArrayList<CalendarEvent>(QueryFactory
				.from(p)
				.where(p.entry.start.after(startDate)
						.or(p.entry.end.before(endDate))
						.and(p.account.id.eq(account.getId())))
				.listResults(p.entry).getResults());
	}
}