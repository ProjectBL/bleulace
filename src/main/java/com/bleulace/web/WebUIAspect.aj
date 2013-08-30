package com.bleulace.web;

import java.io.Serializable;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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