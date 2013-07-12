package com.bleulace.ui.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Scope("session")
@Component("webUI")
@Widgetset("com.vaadin.DefaultWidgetSet")
@Theme("bleulacetheme")
public class WebUI extends UI
{
	private static final long serialVersionUID = 2191009197124553972L;

	@Override
	protected void init(VaadinRequest request)
	{
		setNavigator(new DiscoveryNavigator(this, this));
		getNavigator().navigateTo("front");
	}
}