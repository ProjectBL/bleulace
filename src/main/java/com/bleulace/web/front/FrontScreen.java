package com.bleulace.web.front;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Notification;

@SuppressWarnings("deprecation")
@Configurable
public class FrontScreen extends CustomComponent implements
		LoginForm.LoginListener
{
	@Autowired
	@Qualifier("mainScreen")
	private Component mainScreen;

	public FrontScreen()
	{
		LoginForm form = new LoginForm();
		form.addLoginListener(this);
		setCompositionRoot(form);
	}

	@Override
	public void onLogin(LoginEvent event)
	{
		String username = event.getLoginParameter("username");
		char[] password = event.getLoginParameter("password").toCharArray();
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		try
		{
			SecurityUtils.getSubject().login(token);
			getUI().setContent(mainScreen);
		}
		catch (AuthenticationException e)
		{
			Notification.show("Authentication Failure");
		}
	}
}