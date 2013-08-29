package com.bleulace.web;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class WebUIConfig
{
	@Bean
	@Scope("session")
	@Qualifier("uiBus")
	public EventBus uiBus()
	{
		return new SimpleEventBus();
	}
}