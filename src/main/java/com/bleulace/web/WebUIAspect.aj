package com.bleulace.web;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

aspect WebUIAspect
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

	/**
	 * save nav state in session
	 */
	after(String navState):
		call(public void Navigator.navigateTo(*)) && args(navState) && within(com.bleulace..*)
	{
		SecurityUtils.getSubject().getSession()
				.setAttribute("navState", navState);
	}

	/**
	 * 
	 * @param component
	 *            , the component on which the push-enabled method is declared.
	 *            Acquiring UI reference through Component.getUI() is safer than
	 *            calling UI.getCurrent(), or at least that's what the vaadin
	 *            documentation said...
	 */
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

	after(ViewChangeEvent event) throwing(Exception e) : 
		execution(public void View+.enter(ViewChangeEvent)) && args(event)
	{
		// TODO backup logix
	}

	void around() : execution(void UI+.init(VaadinRequest)) 
	{
		// TODO backup logix
	}
}