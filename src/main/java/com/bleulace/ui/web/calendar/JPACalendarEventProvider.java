package com.bleulace.ui.web.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.JPACalendarEvent;
import com.bleulace.domain.calendar.QCalendarEntryParticipant;
import com.frugalu.api.messaging.jpa.EntityManagerReference;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;

@Configurable
public class JPACalendarEventProvider extends BasicEventProvider implements
		EventChangeNotifier
{
	private static final long serialVersionUID = 6435174386068732471L;

	private JpaRepository<JPACalendarEvent, String> repository = new SimpleJpaRepository<JPACalendarEvent, String>(
			JPACalendarEvent.class, EntityManagerReference.get());

	private Map<String, CalendarEvent> dirtyMap = new HashMap<String, CalendarEvent>();

	private final List<EventChangeListener> eventChangeListeners = new LinkedList<CalendarEvent.EventChangeListener>();

	private final Account account;

	public JPACalendarEventProvider(Account account)
	{
		Assert.notNull(account);
		Assert.isTrue(!account.isNew());
		this.account = account;
	}

	@Override
	@Transactional
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		flushChanges();
		eventList.clear();
		QCalendarEntryParticipant p = QCalendarEntryParticipant.calendarEntryParticipant;
		for (JPACalendarEvent event : QueryFactory
				.from(p)
				.where(p.entry.start.after(startDate)
						.or(p.entry.end.before(endDate))
						.and(p.account.id.eq(account.getId())))
				.listResults(p.entry).getResults())
		{
			if (!eventList.contains(event))
			{
				super.addEvent(event);
			}
		}
		return eventList;
	}

	@Override
	public void addEvent(CalendarEvent event)
	{
		JPACalendarEvent jpaEvent = (JPACalendarEvent) event;
		dirtyMap.put(jpaEvent.getId(), event);
		super.addEvent(event);
	}

	@Override
	public void removeEvent(CalendarEvent event)
	{
		JPACalendarEvent jpaEvent = (JPACalendarEvent) event;
		dirtyMap.put(jpaEvent.getId(), null);
		super.removeEvent(event);
	}

	@Transactional
	public void flushChanges()
	{
		for (String id : dirtyMap.keySet())
		{
			JPACalendarEvent event = (JPACalendarEvent) dirtyMap.get(id);
			if (event == null)
			{
				repository.delete(id);
			}
			else
			{
				repository.save(event);
			}
			dirtyMap.remove(id);
		}
	}

	@Override
	public void eventChange(EventChangeEvent changeEvent)
	{
		JPACalendarEvent event = (JPACalendarEvent) changeEvent
				.getCalendarEvent();
		dirtyMap.put(event.getId(), event);
		for (EventChangeListener listener : eventChangeListeners)
		{
			listener.eventChange(changeEvent);
		}
		super.eventChange(changeEvent);
	}

	@Override
	public void addEventChangeListener(EventChangeListener listener)
	{
		if (!eventChangeListeners.contains(listener))
		{
			eventChangeListeners.add(listener);
		}
	}

	@Override
	public void removeEventChangeListener(EventChangeListener listener)
	{
		eventChangeListeners.remove(listener);
	}
}