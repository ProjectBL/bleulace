package com.bleulace.domain.crm.ui.profile;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.crm.ui.profile.ProfileView.CalendarDirtiedEvent;
import com.bleulace.domain.management.command.RescheduleEventCommand;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.presentation.EventDTOFactory;
import com.bleulace.web.stereotype.Presenter;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;

@Presenter(viewNames = "profileView")
class CalendarPresenter
{
	@Autowired
	@Qualifier("profileCalendar")
	private Calendar calendar;

	@Autowired
	private EventSearchField searchField;

	@Autowired
	private EventDTOFactory factory;

	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	@Autowired
	private CommandGateway gateway;

	@EventHandler
	public void on(RangeSelectEvent event)
	{
		EventDTO dto = factory.make();
		dto.setStart(event.getStart());
		dto.setEnd(event.getEnd());
		UI.getCurrent().addWindow(new EventWindow(dto));
	}

	@EventHandler
	public void on(EventClick event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();

		calendar.setStartDate(dto.getStart());
		calendar.setEndDate(dto.getEnd());

		Window w = new EventWindow((EventDTO) event.getCalendarEvent());
		UI.getCurrent().addWindow(w);
	}

	@EventHandler
	public void on(EventResize event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		gateway.sendAndWait(new RescheduleEventCommand(dto.getId(), event
				.getNewStart(), event.getNewEnd()));
		uiBus.publish(GenericEventMessage
				.asEventMessage(new CalendarDirtiedEvent()));
		Notification.show("Event resized.", Type.TRAY_NOTIFICATION);
	}

	@EventHandler
	public void on(MoveEvent event)
	{
		EventDTO dto = (EventDTO) event.getCalendarEvent();
		gateway.sendAndWait(new RescheduleEventCommand(dto.getId(), event
				.getNewStart()));
		uiBus.publish(GenericEventMessage
				.asEventMessage(new CalendarDirtiedEvent()));
		Notification.show("Event moved.", Type.TRAY_NOTIFICATION);
	}

	@EventHandler
	public void on(CalendarDirtiedEvent event)
	{
		calendar.markAsDirty();
		searchField.markAsDirty();
	}
}