package net.bluelace.ui.web.front;

import java.io.Serializable;

import net.bluelace.domain.account.Account;
import net.bluelace.ui.web.front.FrontView.FrontViewListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.vaadin.ui.Notification;

public class FrontPresenter implements FrontViewListener, Serializable
{
	private final FrontView view;

	public FrontPresenter(FrontView view)
	{
		this.view = view;
	}

	@Override
	public void onLogin(UsernamePasswordToken token)
	{
		String message = null;
		try
		{
			view.setEnabled(false);
			SecurityUtils.getSubject().login(token);
			message = "Welcome back, "
					+ Account.findByEmail(token.getUsername()).getFirstName()
					+ ".";
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