package com.bleulace.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

privileged aspect WebUIAspect
{
	declare parents : @Presenter * implements Serializable;
	declare parents : @VaadinView * implements IVaadinView;

	private interface IVaadinView extends View, Component
	{
	}

	@Autowired
	@Qualifier("uiBus")
	private transient EventBus IVaadinView.uiBus;

	EventBus IVaadinView.getUIBus()
	{
		return uiBus;
	}

	private Set<String> Navigator.viewNames = new HashSet<String>();

	after(Navigator nav) :
		call(* Navigator.addView(String,..))
		&& target(nav)
	{
		nav.viewNames.add((String) thisJoinPoint.getArgs()[0]);
	}

	declare parents : Navigator implements DetachListener;

	public void Navigator.detach(DetachEvent event)
	{
		for (String viewName : viewNames)
		{
			this.removeView(viewName);
		}
	}

	after(UI ui, Navigator nav) : 
		call(public void UI+.setNavigator(Navigator+)) 
		&& target(ui)
		&& args(nav)
	{
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