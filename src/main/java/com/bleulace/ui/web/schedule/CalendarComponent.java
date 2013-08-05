package com.bleulace.ui.web.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalDate;

import com.bleulace.mgt.presentation.EventDTO;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
public class CalendarComponent extends CustomComponent implements
		RangeSelectHandler, EventMoveHandler, EventResizeHandler,
		EventClickHandler, CalendarEventProvider
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6722940036780750080L;

	private final Calendar calendar;

	private final String accountId;

	public CalendarComponent()
	{
		this.accountId = SecurityUtils.getSubject().getId();
		calendar = new Calendar();
		calendar.setStartDate(LocalDate.now().toDateMidnight().toDate());
		calendar.setEndDate(LocalDate.now().plusDays(1).toDateMidnight()
				.toDate());
		calendar.setEventProvider(this);
		// calendar.setHandler((RangeSelectHandler) this);
		// calendar.setHandler((EventMoveHandler) this);
		// calendar.setHandler((EventResizeHandler) this);
		// calendar.setHandler((EventClickHandler) this);

		setCompositionRoot(calendar);
	}

	@Override
	public void eventClick(EventClick event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		System.out.println(event);
	}

	@Override
	public void eventResize(EventResize event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		System.out.println(event);
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		System.out.println(event);
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		// TODO Auto-generated method stub
		System.out.println(event);
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		List<EventDTO> dtos = EventDTO.FINDER.findByAccountIdAndRange(
				accountId, startDate, endDate);
		for (EventDTO dto : dtos)
		{
			System.out.println(dto);
			events.add(dto);
		}
		return events;
	}
}