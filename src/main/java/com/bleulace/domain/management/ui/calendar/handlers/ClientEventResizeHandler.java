package com.bleulace.domain.management.ui.calendar.handlers;

import org.springframework.stereotype.Component;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.command.RescheduleEventCommand;
import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

@Component
class ClientEventResizeHandler extends BasicEventResizeHandler implements
		CommandGatewayAware
{
	@Override
	public void eventResize(EventResize event)
	{
		sendAndWait(new RescheduleEventCommand(
				((EventDTO) event.getCalendarEvent()).getId(),
				event.getNewStart(), event.getNewEnd()));
		super.eventResize(event);
	}
}