package com.bleulace.ui.web.front;

import com.bleulace.ui.infrastructure.VaadinView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@VaadinView
public class FrontView
{
	private static final long serialVersionUID = 5567348045276713883L;

	@Override
	public void enter(ViewChangeEvent event)
	{
		post("");
	}
}