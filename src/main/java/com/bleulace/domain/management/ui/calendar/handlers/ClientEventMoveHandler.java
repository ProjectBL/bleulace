package com.bleulace.domain.management.ui.calendar.handlers;

import org.springframework.stereotype.Component;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.command.RescheduleEventCommand;
import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;

@Component
class ClientEventMoveHandler extends BasicEventMoveHandler implements
		CommandGatewayAware
{
	@Override
	public void eventMove(MoveEvent event)
	{
		sendAndWait(new RescheduleEventCommand(
				((EventDTO) event.getCalendarEvent()).getId(),
				event.getNewStart()));
		super.eventMove(event);
	}
}