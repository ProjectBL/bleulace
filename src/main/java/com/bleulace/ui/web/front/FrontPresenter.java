package com.bleulace.ui.web.front;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;

import com.bleulace.domain.account.Account;
import com.bleulace.ui.web.calendar.CalendarView;
import com.bleulace.ui.web.front.FrontView.FrontViewListener;
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
		String message = null;
		try
		{
			view.setEnabled(false);
			Account account = Account.login(username, password);
			message = "Welcome back, " + account.getFirstName();
			UI.getCurrent().getNavigator().navigateTo(CalendarView.NAME);
		}
		catch (AuthenticationException e)
		{
			message = "Login Failed";
		}
		finally
		{
			Notification.show(message);
			view.setEnabled(true);
		}
	}

	@Override
	public void onRegister(Account account)
	{
		// TODO Auto-generated method stub

	}
}