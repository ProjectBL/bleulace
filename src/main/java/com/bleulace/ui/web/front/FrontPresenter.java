package com.bleulace.ui.web.front;

import java.io.Serializable;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.crm.domain.Account;
import com.bleulace.ui.web.front.FrontView.FrontViewListener;
import com.vaadin.ui.Notification;

public class FrontPresenter implements FrontViewListener, CommandGatewayAware,
		Serializable
{
	private static final long serialVersionUID = 365877728219202183L;

	private final FrontView view;

	public FrontPresenter(FrontView view)
	{
		this.view = view;
	}

	@Override
	public void onLogin(String username, String password)
	{
		view.setEnabled(false);
		Boolean success = gateway().sendAndWait(
				new LoginCommand(username, password));
		if (success)
		{
			Notification.show("SUCCESS");
		}
		else
		{
			Notification.show("FAIL");
		}
		view.setEnabled(true);
	}

	@Override
	public void onRegister(Account account)
	{
		// TODO Auto-generated method stub

	}
}