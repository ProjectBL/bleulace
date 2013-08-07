package com.bleulace.mgt.domain.event;

import com.bleulace.utils.jpa.DateWindow;

public class EventEditedEvent extends EventCreatedEvent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6131887657844911747L;

	public EventEditedEvent(DateWindow window)
	{
		super(window);
	}
}