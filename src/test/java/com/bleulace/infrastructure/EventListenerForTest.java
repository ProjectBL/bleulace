package com.bleulace.infrastructure;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

@Component
class EventListenerForTest
{
	boolean caught = false;

	boolean isCaught()
	{
		return caught;
	}

	@EventHandler
	public void on(EventForTest event)
	{
		caught = true;
	}
}