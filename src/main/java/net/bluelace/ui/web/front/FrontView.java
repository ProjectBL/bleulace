package net.bluelace.ui.web.front;

import net.bluelace.domain.account.Account;

import org.apache.shiro.authc.UsernamePasswordToken;

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