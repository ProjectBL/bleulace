package com.bleulace.domain.management.ui.calendar;

import com.bleulace.domain.management.ui.calendar.CalendarView.CalendarType;
import com.bleulace.domain.management.ui.calendar.CalendarView.CalendarViewListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.handler.BasicBackwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicForwardHandler;

class CalendarPresenter implements CalendarViewListener
{
	private static final DateClickHandler BASIC_DATE_CLICK_HANDLER = new BasicDateClickHandler();
	private static final ForwardHandler BASIC_FORWARD_HANDLER = new BasicForwardHandler();
	private static final BackwardHandler BASIC_BACKWARD_HANDLER = new BasicBackwardHandler();

	@Override
	public void dateClick(DateClickEvent event)
	{
		BASIC_DATE_CLICK_HANDLER.dateClick(event);
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
	}

	@Override
	public void forward(ForwardEvent event)
	{
		BASIC_FORWARD_HANDLER.forward(event);
	}

	@Override
	public void backward(BackwardEvent event)
	{
		BASIC_BACKWARD_HANDLER.backward(event);
	}

	@Override
	public void timeslotSelect(TimeSlot timeslot)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void calendarTypeSelect(CalendarType type)
	{
		// TODO Auto-generated method stub

	}

	public View createView()
	{
		CalendarViewImpl impl = new CalendarViewImpl();
		impl.addViewListener(this);
		return impl;
	}
}