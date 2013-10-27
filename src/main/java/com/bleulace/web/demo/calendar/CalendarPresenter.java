package com.bleulace.web.demo.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.web.annotation.Presenter;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEditableEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeNotifier;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

@Presenter
class CalendarPresenter implements RangeSelectHandler, EventClickHandler,
		EventResizeHandler, EventMoveHandler, DateClickHandler,
		CalendarEditableEventProvider, EventSetChangeNotifier,
		CalendarEvent.EventChangeListener
{
	private List<EventSetChangeListener> listeners = new ArrayList<EventSetChangeListener>();

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private TabSheet tabSheet;

	@Autowired
	private DateField dateField;

	@Autowired
	private Calendar calendar;

	@Autowired
	private CalendarView view;

	private final DateClickHandler basicDateClickHandler = new BasicDateClickHandler();
	private final BasicEventMoveHandler basicEventMoveHandler = new BasicEventMoveHandler();
	private final BasicEventResizeHandler basicEventResizeHandler = new BasicEventResizeHandler();

	void cursorChanged()
	{
		tabSheet.setSelectedTab(CalendarSelection.DAY.ordinal());
		tabSelected(CalendarSelection.DAY);
	}

	void statusUpdated(String status)
	{
	}

	void tabSelected(CalendarSelection selection)
	{
		Range<Date> range = selection.command.execute(dateField.getValue());
		calendar.setStartDate(range.getMinimum());
		calendar.setEndDate(range.getMaximum());
	}

	void eventAccepted(PersistentEvent event)
	{
		updateRsvp(event, RsvpStatus.ACCEPTED);
	}

	void eventDeclined(PersistentEvent event)
	{
		updateRsvp(event, RsvpStatus.DECLINED);
	}

	void searchExecuted(String searchTerm)
	{
	}

	@Override
	public void eventClick(EventClick event)
	{
		view.showTimeBox((PersistentEvent) event.getCalendarEvent());
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		PersistentEvent calendarEvent = new PersistentEvent();
		calendarEvent.setStart(event.getStart());
		calendarEvent.setEnd(event.getEnd());

		Account current = Account.getCurrent();
		calendarEvent.getInvitees().put(current,
				new EventInvitee(current, current, RsvpStatus.ACCEPTED));

		view.showTimeBox(calendarEvent);
	}

	@Override
	public void dateClick(DateClickEvent event)
	{
		tabSheet.setSelectedTab(CalendarSelection.DAY.ordinal());
		dateField.setValue(event.getDate());
		basicDateClickHandler.dateClick(event);
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		basicEventMoveHandler.eventMove(event);
		eventDAO.save((PersistentEvent) event.getCalendarEvent());
	}

	@Override
	public void eventResize(EventResize event)
	{
		basicEventResizeHandler.eventResize(event);
		eventDAO.save((PersistentEvent) event.getCalendarEvent());
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		for (PersistentEvent event : eventDAO.findEvents(startDate, endDate,
				Account.getCurrent().getId()))
		{
			event.addEventChangeListener(this);
			events.add(event);
		}
		return events;
	}

	@Override
	public void addEvent(CalendarEvent calendarEvent)
	{
		fireEventSetChange();
	}

	@Override
	public void removeEvent(CalendarEvent calendarEvent)
	{
		PersistentEvent event = (PersistentEvent) calendarEvent;
		eventDAO.delete(event);
		fireEventSetChange();
	}

	@Override
	public void addEventSetChangeListener(EventSetChangeListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeEventSetChangeListener(EventSetChangeListener listener)
	{
		listeners.remove(listener);
	}

	@Override
	public void eventChange(EventChangeEvent eventChangeEvent)
	{
		fireEventSetChange();
	}

	protected void fireEventSetChange()
	{
		EventSetChangeEvent event = new EventSetChangeEvent(this);

		for (EventSetChangeListener listener : listeners)
		{
			listener.eventSetChange(event);
		}
	}

	private void updateRsvp(PersistentEvent event, RsvpStatus status)
	{
		event.setRsvpStatus(Account.getCurrent().getId(), status);
		eventDAO.save(event);
		event.setStyleName(null);
		calendar.markAsDirty();
	}
}