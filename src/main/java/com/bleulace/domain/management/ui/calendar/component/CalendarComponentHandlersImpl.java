package com.bleulace.domain.management.ui.calendar.component;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.component.CalendarComponent.CalendarComponentListener;
import com.bleulace.jpa.DateWindow;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicBackwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicForwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicWeekClickHandler;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class CalendarComponentHandlersImpl implements CalendarComponentHandlers
{
	private List<CalendarComponentListener> listeners;

	private static final BackwardHandler BACKWARD = new BasicBackwardHandler();
	private static final ForwardHandler FORWARD = new BasicForwardHandler();
	private static final DateClickHandler DATECLICK = new BasicDateClickHandler();
	private static final WeekClickHandler WEEKCLICK = new BasicWeekClickHandler();

	CalendarComponentHandlersImpl()
	{
	}

	@Override
	public void setComponentListeners(List<CalendarComponentListener> listeners)
	{
		this.listeners = listeners;
	}

	@Override
	public void eventClick(EventClick event)
	{
		for (CalendarComponentListener l : listeners)
		{
			l.eventSelected((EventDTO) event.getCalendarEvent());
		}
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		for (CalendarComponentListener l : listeners)
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

	@Override
	public void datesChanged(Range<Date> oldDates, Range<Date> newDates)
	{
		if (!oldDates.equals(newDates))
		{
			doDateChangeNotification(oldDates.getMinimum(),
					oldDates.getMaximum(), newDates.getMinimum(),
					newDates.getMaximum());
		}
	}

	@Override
	public void doDateChangeNotification(Date oldStart, Date oldEnd,
			Date newStart, Date newEnd)
	{
		for (CalendarComponentListener l : listeners)
		{
			l.visibleDatesChange(oldStart, oldEnd, newStart, newEnd);
		}
	}

	private void notifyEventDragged(EventDTO dto, Date start, Date end)
	{
		for (CalendarComponentListener l : listeners)
		{
			l.eventDragged(dto, start, end);
		}
	}
}
