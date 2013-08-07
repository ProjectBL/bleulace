package com.bleulace.ui.web.calendar;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.bleulace.ui.infrastructure.VaadinView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

@VaadinView
public class ScheduleView extends CustomComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4951420487072784666L;

	public ScheduleView()
	{
	}

	@RequiresAuthentication
	@Override
	public void enter(ViewChangeEvent event)
	{
		setCompositionRoot(new CalendarComponent());
	}
}