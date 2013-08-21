package com.bleulace.domain.crm.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
@Scope("session")
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
