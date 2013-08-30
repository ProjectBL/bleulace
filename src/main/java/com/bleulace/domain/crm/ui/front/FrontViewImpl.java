package com.bleulace.domain.crm.ui.front;

import javax.annotation.PostConstruct;

import org.axonframework.domain.GenericDomainEventMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.web.stereotype.Screen;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;

@Screen("frontView")
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
	public void showLoginFailure()
	{
		Notification.show("FAIL");
	}

	@Override
	public void showLoginSuccess(UserDTO dto)
	{
		Notification.show("Welcome back, " + dto.getFirstName() + ".");
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		getUIBus().publish(GenericDomainEventMessage.asEventMessage(event));
	}

	@Override
	public void clearLoginParams()
	{
		loginForm.clearValues();
	}
}
