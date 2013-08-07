package com.bleulace.ui.web.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.MoveEventCommand;
import com.bleulace.mgt.application.command.ResizeEventCommand;
import com.bleulace.mgt.presentation.EventDTO;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;
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
		EventClickHandler, CalendarEventProvider, CommandGatewayAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6722940036780750080L;

	private Calendar calendar;

	private final String accountId;

	public CalendarComponent()
	{
		this.accountId = SecurityUtils.getSubject().getId();
		buildCalendar();
	}

	@Override
	public void eventClick(EventClick event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		Window editor = new Window();
		editor.setContent(new EventEditorForm(dto));
		getUI().addWindow(editor);
		buildCalendar();
	}

	@Override
	public void eventResize(EventResize event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		gateway().sendAndWait(
				new ResizeEventCommand(dto.getId(), event.getNewStart(), event
						.getNewEnd()));
		buildCalendar();
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		gateway().sendAndWait(
				new MoveEventCommand(dto.getId(), event.getNewStart()));
		buildCalendar();
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		buildCalendar();
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		List<EventDTO> dtos = EventDTO.FINDER.findByAccountIdAndRange(
				accountId, startDate, endDate);
		for (EventDTO dto : dtos)
		{
			events.add(dto);
		}
		return events;
	}

	private void buildCalendar()
	{
		calendar = new Calendar();
		calendar.setEventProvider(this);
		calendar.setHandler((RangeSelectHandler) this);
		calendar.setHandler((EventMoveHandler) this);
		calendar.setHandler((EventResizeHandler) this);
		calendar.setHandler((EventClickHandler) this);
		setCompositionRoot(calendar);
	}
}