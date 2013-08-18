package com.bleulace.domain.management.ui.calendar.context;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.command.RescheduleEventCommand;
import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

@Component
class SelfCalendarHandlers implements CalendarViewContext.CalendarHandlers,
		CommandGatewayAware
{
	private static final EventMoveHandler BASIC_EVENT_MOVE_HANDLER = new BasicEventMoveHandler();

	private static final EventResizeHandler BASIC_EVENT_RESIZE_HANDLER = new BasicEventResizeHandler();

	@Override
	public void eventClick(EventClick event)
	{
		// calendar.addEvent(bla);

	}

	@Override
	public void dateClick(DateClickEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void eventResize(EventResize event)
	{
		try
		{
			if (event.getCalendarEvent() instanceof EventDTO)
			{
				EventDTO dto = (EventDTO) event.getCalendarEvent();
				sendAndWait(new RescheduleEventCommand(dto.getId(),
						event.getNewStart(), event.getNewEnd()));
			}
			BASIC_EVENT_RESIZE_HANDLER.eventResize(event);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Ugh.", e);
		}
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		try
		{
			if (event.getCalendarEvent() instanceof EventDTO)
			{
				EventDTO dto = (EventDTO) event.getCalendarEvent();
				sendAndWait(new RescheduleEventCommand(dto.getId(),
						event.getNewStart()));
			}
			BASIC_EVENT_MOVE_HANDLER.eventMove(event);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Ugh.", e);
		}
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		// TODO Auto-generated method stub

	}
}
