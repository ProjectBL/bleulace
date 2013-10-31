package com.bleulace.utils.event;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bleulace.web.annotation.WebProfile;
import com.google.common.eventbus.EventBus;

@Configuration
@WebProfile
class EventBusConfig
{
	@Bean
	public EventBus eventBus(Executor executor)
	{
		return new EventBus(// AsyncEventBus("GLOBAL", executor
		);
	}

	@Bean
	public EventBusPostProcessor eventBusPostProcessor(EventBus eventBus)
	{
		return new EventBusPostProcessor(eventBus);
	}
}