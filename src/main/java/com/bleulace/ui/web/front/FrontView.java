package com.bleulace.ui.web.front;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.cqrs.event.EventBusPublisher;
import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.ui.infrastructure.ServerPush;
import com.bleulace.ui.infrastructure.VaadinView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Notification;

@VaadinView
public class FrontView extends CustomComponent implements
		LoginForm.LoginListener, EventBusPublisher
{
	private static final long serialVersionUID = 5567348045276713883L;

	FrontView()
	{
		LoginForm l = new LoginForm();
		l.addListener(this);
		setCompositionRoot(l);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
	}

	@EventHandler
	@ServerPush
	public void on(final LoginEvent event)
	{
		Notification.show(event.toString());
	}

	@Override
	public void onLogin(LoginEvent event)
	{
		post(event);
		gateway().send(
				new LoginCommand(event.getLoginParameter("username"), event
						.getLoginParameter("password")));
	}
}