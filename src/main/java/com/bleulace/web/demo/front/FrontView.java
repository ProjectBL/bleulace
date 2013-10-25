package com.bleulace.web.demo.front;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.web.annotation.VaadinView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

@VaadinView
class FrontView extends CustomComponent
{
	@Autowired
	private FrontPresenter presenter;

	@Autowired
	private Component loginForm;

	@PostConstruct
	protected void init()
	{
		setCompositionRoot(loginForm);
	}

	@RequiresGuest
	@Override
	public void enter(ViewChangeEvent event)
	{
	}
}