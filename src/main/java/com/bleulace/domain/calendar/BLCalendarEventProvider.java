package com.bleulace.domain.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.bluelace.domain.calendar.QCalendarEntry;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.account.Account;
import com.vaadin.ui.components.calendar.event.CalendarEditableEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

@Configurable
public class BLCalendarEventProvider implements CalendarEditableEventProvider
{
	private static final long serialVersionUID = -3137817327870498481L;

	@PersistenceContext
	private EntityManager entityManager;

	private final Account account;

	public BLCalendarEventProvider(Account account)
	{
		this.account = account;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		if (account != null && !account.isNew())
		{
			QCalendarEntry e = QCalendarEntry.calendarEntry;
			List<CalendarEntry> entries = QueryFactory
					.from(e)
					.where(e.start.after(startDate).and(e.end.before(endDate))
							.and(e.participants.containsKey(account)))
					.orderBy(e.start.asc()).listResults(e).getResults();
			for (CalendarEntry entry : entries)
			{
				events.add(entry);
			}
		}
		return events;
	}

	@Override
	@Transactional
	public void addEvent(CalendarEvent event)
	{
		if (!account.isNew())
		{
			ModelMapper mapper = new ModelMapper();
			CalendarEntry entry = mapper.map(event, CalendarEntry.class);
			entityManager.persist(entry);
		}
	}

	@Override
	@Transactional
	public void removeEvent(CalendarEvent event)
	{
		entityManager.remove(event);
	}
}