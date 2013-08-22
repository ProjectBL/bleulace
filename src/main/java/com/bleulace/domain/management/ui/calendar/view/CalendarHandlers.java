package com.bleulace.domain.management.ui.calendar.view;

import java.util.Date;
import java.util.List;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.view.CalendarView.CalendarViewListener;
import com.bleulace.jpa.DateWindow;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicBackwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicForwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicWeekClickHandler;

class CalendarHandlers implements EventClickHandler, RangeSelectHandler,
		EventResizeHandler, EventMoveHandler, ForwardHandler, BackwardHandler,
		DateClickHandler, WeekClickHandler
{
	private List<CalendarViewListener> listeners;

	private static final BackwardHandler BACKWARD = new BasicBackwardHandler();
	private static final ForwardHandler FORWARD = new BasicForwardHandler();
	private static final DateClickHandler DATECLICK = new BasicDateClickHandler();
	private static final WeekClickHandler WEEKCLICK = new BasicWeekClickHandler();

	CalendarHandlers(List<CalendarViewListener> listeners)
	{
		this.listeners = listeners;
	}

	@Override
	public void eventClick(EventClick event)
	{
		for (CalendarViewListener l : listeners)
		{
			l.eventSelected((EventDTO) event.getCalendarEvent());
		}
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		for (CalendarViewListener l : listeners)
		{
			l.rangeSelected(event.getStart(), event.getEnd());
		}
	}

	@Override
	public void eventResize(EventResize event)
	{
		notifyEventDragged((EventDTO) event.getCalendarEvent(),
				event.getNewStart(), event.getNewEnd());
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		DateWindow window = new DateWindow(dto.getStart(), dto.getEnd())
				.move(event.getNewStart());
		notifyEventDragged(dto, window.getStart(), window.getEnd());
	}

	@Override
	public void weekClick(WeekClick event)
	{
		WEEKCLICK.weekClick(event);
	}

	@Override
	public void dateClick(DateClickEvent event)
	{
		DATECLICK.dateClick(event);
	}

	@Override
	public void backward(BackwardEvent event)
	{
		BACKWARD.backward(event);
	}

	@Override
	public void forward(ForwardEvent event)
	{
		FORWARD.forward(event);
	}

	private void notifyEventDragged(EventDTO dto, Date start, Date end)
	{
		for (CalendarViewListener l : listeners)
		{
			l.eventMoved(dto, start, end);
		}
	}
}
