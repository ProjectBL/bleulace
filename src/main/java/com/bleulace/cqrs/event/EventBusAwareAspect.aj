package com.bleulace.cqrs.event;

import org.axonframework.eventhandling.EventBus;

import com.bleulace.utils.ctx.SpringApplicationContext;

aspect EventBusAwareAspect
{
	private static final transient EventBus EVENTBUS = SpringApplicationContext
			.get().getBean(EventBus.class);

	/**
	 * 
	 * @return a configured event bus ready for use
	 */
	EventBus EventBusAware.eventBus()
	{
		return EVENTBUS;
	}
}
