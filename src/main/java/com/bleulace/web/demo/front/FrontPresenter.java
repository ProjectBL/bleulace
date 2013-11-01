package com.bleulace.web.demo.front;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.web.annotation.Presenter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@Presenter
public class FrontPresenter
{
	@Autowired
	private BeanFieldGroup<LoginModel> loginFieldGroup;

	public void react()
	{
		Subject s = SecurityUtils.getSubject();
		UI.getCurrent()
				.getNavigator()
				.navigateTo(
						(s.isAuthenticated() || s.isRemembered()) ? "profileView/"
								+ s.getPrincipal()
								: "frontView");
	}

	void loginClicked()
	{
		LoginModel model = loginFieldGroup.getItemDataSource().getBean();
		try
		{
			SecurityUtils
					.getSubject()
					.login(new UsernamePasswordToken(model.getUsername(), model
							.getPassword().toCharArray(), model.isRememberMe()));
			clearLoginFields();
		}
		catch (AuthenticationException e)
		{
			loginFailure();
		}
		react();
	}

	void logoutClicked()
	{
		SecurityUtils.getSubject().logout();
		react();
	}

	void loginFailure()
	{
		Notification.show("Authentication failure.");
	}

	private void clearLoginFields()
	{
		loginFieldGroup.setItemDataSource(new LoginModel());
	}
}