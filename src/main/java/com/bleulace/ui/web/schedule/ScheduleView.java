package com.bleulace.ui.web.schedule;

import com.bleulace.ui.infrastructure.VaadinView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@VaadinView
public class ScheduleView extends CustomComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4951420487072784666L;

	public ScheduleView()
	{
		setCompositionRoot(new Label("IMPLEMENT ME!"));
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		return;
	}
}