package com.bleulace.domain.calendar.command;

import com.bleulace.domain.Command;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;

class MoveEventCommand implements Command
{
	private final MoveEvent event;

	private static final BasicEventMoveHandler HANDLER = new BasicEventMoveHandler();

	public MoveEventCommand(MoveEvent event)
	{
		this.event = event;
	}

	@Override
	public void execute()
	{
		HANDLER.eventMove(event);
	}
}