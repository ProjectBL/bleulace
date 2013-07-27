package com.bleulace.ui.web.front;

import java.io.Serializable;

import org.springframework.stereotype.Controller;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.crm.presentation.AccountDTO;
import com.bleulace.ui.web.front.LoginBar.LoginBarListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@Controller
public class LoginBarController implements LoginBarListener,
		CommandGatewayAware, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -322873096452363886L;

	@Override
	public void onLogin(String username, String password, Boolean rememberMe)
	{
		if (gateway().sendAndWait(
				new LoginCommand(username, password, rememberMe)))
		{
			UI.getCurrent().getNavigator().navigateTo("homeView");
			AccountDTO dto = AccountDTO.FINDER.findByUsername(username);
			Notification.show("Welcome back " + dto.getFirstName());
		}
		else
		{
			Notification.show("Access Denied");
		}
	}
}