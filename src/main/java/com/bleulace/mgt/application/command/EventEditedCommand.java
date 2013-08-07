package com.bleulace.mgt.application.command;

import com.bleulace.mgt.domain.event.EventCreatedEvent;
import com.bleulace.utils.jpa.DateWindow;

public class EventEditedCommand extends EventCreatedEvent
{
	private static final long serialVersionUID = -872194081563005383L;

	public EventEditedCommand(DateWindow window)
	{
		super(window);
	}
}