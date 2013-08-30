package com.bleulace.domain.crm.ui.front;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.presentation.UserFinder;
import com.bleulace.web.stereotype.Presenter;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

@Presenter(viewNames = "frontView")
class FrontPresenter
{
	@Autowired
	private FrontView view;

	@Autowired
	private UserFinder finder;

	@EventHandler
	public void on(ViewChangeEvent event)
	{
		view.clearLoginParams();
	}

	@EventHandler
	public void on(UsernamePasswordToken token)
	{
		try
		{
			view.setEnabled(false);

			SecurityUtils.getSubject().login(token);
			String id = SecurityUtils.getSubject().getId();
			view.clearLoginParams();

			view.showLoginSuccess(finder.findById(id));
			UI.getCurrent().getNavigator().navigateTo("profileView/" + id);
		}
		catch (AuthenticationException e)
		{
			view.showLoginFailure();
		}
		finally
		{
			view.setEnabled(true);
		}
	}
}
