package com.bleulace.ui.web.calendar;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.calendar.JPACalendarEvent;
import com.frugalu.api.messaging.jpa.EntityManagerReference;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

class JPACalendarEventProvider extends BasicEventProvider implements
		EventChangeNotifier
{
	private static final long serialVersionUID = 6435174386068732471L;

	private transient JpaRepository<JPACalendarEvent, String> repository = new SimpleJpaRepository<JPACalendarEvent, String>(
			JPACalendarEvent.class, EntityManagerReference.get());

	private Map<String, CalendarEvent> dirtyMap = new HashMap<String, CalendarEvent>();

	private final List<EventChangeListener> eventChangeListeners = new LinkedList<CalendarEvent.EventChangeListener>();

	private CalendarEventProvider delegate;

	private CalendarEventPostProcessor postProcessor = new DoNothingPostProcessor();

	public JPACalendarEventProvider(CalendarEventProvider delegate)
	{
		this.delegate = delegate;
	}

	public CalendarEventProvider getDelegate()
	{
		return delegate;
	}

	public void setDelegate(CalendarEventProvider delegate)
	{
		this.delegate = delegate;
	}

	public CalendarEventPostProcessor getPostProcessor()
	{
		return postProcessor;
	}

	public void setPostProcessor(CalendarEventPostProcessor postProcessor)
	{
		this.postProcessor = postProcessor;
	}

	@Override
	@Transactional
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		flushChanges();
		eventList.clear();
		for (CalendarEvent event : delegate.getEvents(startDate, endDate))
		{
			event = postProcessor.process(event, startDate, endDate);
			if (event != null)
			{
				super.addEvent(event);
			}
		}
		return eventList;
	}

	@Override
	public final void addEvent(CalendarEvent event)
	{
		JPACalendarEvent jpaEvent = (JPACalendarEvent) event;
		dirtyMap.put(jpaEvent.getId(), event);
		super.addEvent(event);
	}

	@Override
	public final void removeEvent(CalendarEvent event)
	{
		JPACalendarEvent jpaEvent = (JPACalendarEvent) event;
		dirtyMap.put(jpaEvent.getId(), null);
		super.removeEvent(event);
	}

	@Override
	public final void eventChange(EventChangeEvent changeEvent)
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

	public interface CalendarEventPostProcessor extends Serializable
	{
		public CalendarEvent process(CalendarEvent event, Date start, Date end);
	}

	@Override
	public void removeEventChangeListener(EventChangeListener listener)
	{
		eventChangeListeners.remove(listener);
	}

	@Transactional
	private void flushChanges()
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

	private static class DoNothingPostProcessor implements
			CalendarEventPostProcessor
	{
		private static final long serialVersionUID = 3586376177403362614L;

		@Override
		public CalendarEvent process(CalendarEvent event, Date start, Date end)
		{
			return event;
		}
	}
}