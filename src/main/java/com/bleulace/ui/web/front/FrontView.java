package com.bleulace.ui.web.front;

import com.bleulace.ui.infrastructure.VaadinView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@VaadinView
public class FrontView extends CustomComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2862278918968899497L;

	public FrontView()
	{
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		setCompositionRoot(new LoginForm());
	}
}
