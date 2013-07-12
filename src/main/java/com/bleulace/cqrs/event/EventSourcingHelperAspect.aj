package com.bleulace.cqrs.event;

import java.io.Serializable;

import org.axonframework.eventhandling.EventBus;

import com.bleulace.context.utils.SpringApplicationContext;

aspect EventSourcingHelperAspect
{
	/**
	 * Classes whose name and subpackages cause them to appear likely to be used
	 * as event payloads will automatically declare themselves as
	 * {@link Serializable}
	 */
	declare parents : com.bleulace..event.*Event implements Serializable;

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