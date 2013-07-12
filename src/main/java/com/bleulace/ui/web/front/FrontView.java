package com.bleulace.ui.web.front;

import com.bleulace.accountRelations.domain.Account;
import com.vaadin.navigator.View;

public interface FrontView extends View
{
	public static final Class<? extends FrontView> VIEW = FrontViewImpl.class;

	public void addListener(FrontViewListener listener);

	public void setEnabled(boolean enabled);

	public interface FrontViewListener
	{
		public void onLogin(String username, String password);

		public void onRegister(Account account);
	}
}