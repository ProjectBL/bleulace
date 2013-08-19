package com.bleulace.domain.crm.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
class FrontView extends CustomComponent implements View
{
	FrontView()
	{
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		setCompositionRoot(new LoginForm());
	}
}
