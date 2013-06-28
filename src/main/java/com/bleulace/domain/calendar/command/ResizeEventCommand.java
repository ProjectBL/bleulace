package com.bleulace.domain.calendar.command;

import com.bleulace.domain.Command;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

class ResizeEventCommand implements Command
{
	private static final EventResizeHandler HANDLER = new BasicEventResizeHandler();

	private final EventResize event;

	public ResizeEventCommand(EventResize event)
	{
		this.event = event;
	}

	@Override
	public void execute()
	{
		HANDLER.eventResize(event);
	}
}
