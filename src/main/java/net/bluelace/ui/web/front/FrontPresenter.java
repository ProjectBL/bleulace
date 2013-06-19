package net.bluelace.ui.web.front;

import java.io.Serializable;

import net.bluelace.domain.account.Account;
import net.bluelace.ui.web.front.FrontView.FrontViewListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

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
			UI.getCurrent().getNavigator().navigateTo("calendar");
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