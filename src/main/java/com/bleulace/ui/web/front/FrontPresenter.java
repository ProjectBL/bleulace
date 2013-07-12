package com.bleulace.ui.web.front;

import java.io.Serializable;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.account.messaging.LoginCommand;
import com.bleulace.ui.web.calendar.CalendarView;
import com.bleulace.ui.web.front.FrontView.FrontViewListener;
import com.bleulace.utils.CommandFactory;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class FrontPresenter implements FrontViewListener, Serializable
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
		boolean result = CommandFactory
				.make(new LoginCommand(username, password), Boolean.class)
				.send().getFirstResult();
		view.setEnabled(true);
		String message = result ? "Welcome back, "
				+ Account.current().getFirstName() + "." : "Access Denied.";
		Notification.show(message);
		if (result)
		{
			UI.getCurrent().getNavigator().navigateTo(CalendarView.NAME);
		}

	}

	@Override
	public void onRegister(Account account)
	{
		// TODO Auto-generated method stub

	}
}