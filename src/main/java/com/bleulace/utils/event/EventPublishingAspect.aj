package com.bleulace.utils.event;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.google.common.eventbus.EventBus;

aspect EventPublishingAspect
{
	private final EventBus eventBus = SpringApplicationContext
			.getBean(EventBus.class);

	after() returning(Object message) : 
		execution(@Publish Object+ *.*(..)) 
	{
		if (message != null)
		{
			eventBus.post(message);
		}
	}
}
