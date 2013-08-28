package com.bleulace.domain.crm.ui.front;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.web.Presenter;
import com.vaadin.ui.UI;

@Presenter(viewNames = "frontView")
class FrontPresenter
{
	@Autowired
	private FrontView view;

	@EventHandler
	public void on(UsernamePasswordToken token)
	{
		try
		{
			view.setEnabled(false);
			SecurityUtils.getSubject().login(token);
			UI.getCurrent()
					.getNavigator()
					.navigateTo(
							"calendarView/"
									+ SecurityUtils.getSubject().getId());
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
