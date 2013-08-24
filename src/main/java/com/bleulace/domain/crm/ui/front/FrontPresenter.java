package com.bleulace.domain.crm.ui.front;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
@DependsOn("presenterPostProcessor")
class FrontPresenter
{
	@Autowired
	private FrontView view;

	@EventHandler
	public void on(UsernamePasswordToken token)
	{
		System.out.println(token);
		try
		{
			view.setEnabled(false);
			SecurityUtils.getSubject().login(token);
		}
		catch (AuthenticationException e)
		{
			view.showFailure();
		}
		finally
		{
			view.setEnabled(true);
		}
	}
}
