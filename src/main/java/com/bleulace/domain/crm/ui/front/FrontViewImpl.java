package com.bleulace.domain.crm.ui.front;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.web.VaadinView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;

@VaadinView("frontView")
public class FrontViewImpl extends CustomComponent implements FrontView, View
{
	@Autowired
	private LoginForm loginForm;

	public FrontViewImpl()
	{
	}

	@PostConstruct
	protected void init()
	{
		setCompositionRoot(loginForm);
	}

	@Override
	public void showFailure()
	{
		Notification.show("FAIL");
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
	}
}
