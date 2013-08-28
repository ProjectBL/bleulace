package com.bleulace.web;

import java.util.Map.Entry;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

@Configuration
class WebUIConfig
{
	@Autowired
	private ApplicationContext ctx;

	@Bean
	@Scope("session")
	public WebUI webUI(PresenterSubscribingListener listener)
	{
		WebUI ui = new WebUI();
		ui.setNavigator(makeNavigator(ui));
		ui.getNavigator().addViewChangeListener(listener);
		return ui;
	}

	@Bean
	@Scope("session")
	@Qualifier("uiBus")
	public EventBus uiBus()
	{
		return new SimpleEventBus();
	}

	private Navigator makeNavigator(UI ui)
	{
		Navigator navigator = new Navigator(ui, ui);

		for (Entry<String, View> entry : ctx.getBeansOfType(View.class)
				.entrySet())
		{
			navigator.addView(entry.getKey(), entry.getValue());
		}

		// hack to eagerly load presenters
		ctx.getBeansWithAnnotation(Presenter.class).values();

		return navigator;
	}
}