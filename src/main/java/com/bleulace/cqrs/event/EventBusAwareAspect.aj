package com.bleulace.cqrs.event;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;

import com.bleulace.cqrs.ShiroMetaData;
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

	void EventBusPublisher.post(Object event)
	{
		EVENTBUS.publish(GenericEventMessage.asEventMessage(event)
				.withMetaData(ShiroMetaData.get()));
	}
}
