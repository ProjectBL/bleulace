package com.bleulace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

@Configuration
class WebUIConfig
{
	@Autowired
	private ApplicationContext ctx;

	@Bean
	@Scope("prototype")
	public WebUI webUI()
	{
		WebUI ui = new WebUI();
		ui.setNavigator(makeNavigator(ui));
		return ui;
	}

	private Navigator makeNavigator(UI ui)
	{
		Navigator navigator = new Navigator(ui, ui);

		for (BusRegisteringViewProvider provider : ctx.getBeansOfType(
				BusRegisteringViewProvider.class).values())
		{
			navigator.addProvider(provider);
			navigator.addViewChangeListener(provider);
		}
		return navigator;
	}
}