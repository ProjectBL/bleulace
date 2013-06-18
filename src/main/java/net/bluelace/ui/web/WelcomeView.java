package net.bluelace.ui.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

@Scope("session")
@Component
@SuppressWarnings("deprecation")
public class WelcomeView extends CustomComponent implements LoginListener
{
	private static final long serialVersionUID = 1199488980233798486L;

	public WelcomeView()
	{
		LoginForm loginform = new LoginForm();
		loginform.addLoginListener(this);
		Panel loginPanel = new Panel("Log in", loginform);
		setCompositionRoot(loginPanel);

	}

	@Override
	public void onLogin(LoginEvent event)
	{
		String username = event.getLoginParameter("username");
		System.out.println(username);
		String password = event.getLoginParameter("password");
		System.out.println(password);
		try
		{
			SecurityUtils.getSubject().login(
					new UsernamePasswordToken(username, password));
			Notification.show("Login success");
		}
		catch (AuthenticationException e)
		{
			Notification.show("Login failure");
		}
	}

}