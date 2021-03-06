package com.bleulace.web.demo;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.bleulace.web.annotation.WebProfile;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.vaadin.ui.UI;

@Configuration
@WebProfile
class EventBusConfig
{
	@Bean
	@Scope(value = "ui", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public EventBus uiBus()
	{
		return new EventBus("UI-" + UI.getCurrent().getId());
	}

	@Bean
	public EventBus eventBus(Executor executor)
	{
		return new AsyncEventBus("GLOBAL", executor);
	}
}