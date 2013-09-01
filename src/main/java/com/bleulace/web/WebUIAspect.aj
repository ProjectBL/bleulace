package com.bleulace.web;

import java.io.Serializable;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.axonframework.eventhandling.EventBus;
import org.dellroad.stuff.vaadin7.SpringVaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.stereotype.Presenter;
import com.bleulace.web.stereotype.Screen;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

aspect WebUIAspect
{
	declare parents : @Presenter * implements Serializable;

	declare parents : @Screen * implements ScreenView;

	private interface ScreenView extends View, Component
	{
	}

	@Autowired
	@Qualifier("uiBus")
	private transient EventBus ScreenView.uiBus;

	EventBus ScreenView.getUIBus()
	{
		return uiBus;
	}

	ConfigurableWebApplicationContext around() : 
		execution(ConfigurableWebApplicationContext SpringVaadinSession.getApplicationContext())
	{
		ConfigurableWebApplicationContext ctx = proceed();
		ctx.setParent(SpringApplicationContext.get());
		return ctx;
	}

	@SuppressAjWarnings
	void around(final Component component) : 
		execution(@EnablePush void *(..)) && this(component)
	{
		component.getUI().access(new Runnable()
		{
			@Override
			public void run()
			{
				proceed(component);
			}
		});
	}
}