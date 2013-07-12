package com.bleulace.ui.web.front;

import java.io.Serializable;

import com.bleulace.accountRelations.domain.Account;
import com.bleulace.ui.web.front.FrontView.FrontViewListener;

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
		System.out.println("Fire login command");
		view.setEnabled(true);
	}

	@Override
	public void onRegister(Account account)
	{
		// TODO Auto-generated method stub

	}
}