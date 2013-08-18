package com.bleulace.domain.management.ui.calendar.context;

import org.springframework.stereotype.Component;

import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;

@Component
class OtherCalendarViewHandlers implements CalendarViewContext.CalendarHandlers
{
	@Override
	public void eventResize(EventResize event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void eventMove(MoveEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void eventClick(EventClick event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dateClick(DateClickEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		// TODO Auto-generated method stub

	}
}