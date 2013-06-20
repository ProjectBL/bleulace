package com.bleulace.ui.web.front;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.bleulace.domain.account.Account;
import com.vaadin.navigator.View;

public interface FrontView extends View
{
	public void addListener(FrontViewListener listener);

	public void setEnabled(boolean enabled);

	public interface FrontViewListener
	{
		public void onLogin(UsernamePasswordToken token);

		public void onRegister(Account account);
	}
}