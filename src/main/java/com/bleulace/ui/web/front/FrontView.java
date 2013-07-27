package com.bleulace.ui.web.front;

import com.bleulace.ui.infrastructure.VaadinView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

@VaadinView
public class FrontView extends CustomComponent
{
	private static final long serialVersionUID = 4860935860876735922L;

	public FrontView()
	{
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(new LoginBar());
		setCompositionRoot(layout);
	}
}