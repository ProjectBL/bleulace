package com.bleulace.web.demo.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.web.annotation.WebProfile;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@WebProfile
@Configuration
class FrontComponentConfig
{
	@Autowired
	private FrontPresenter presenter;

	@Bean
	@Scope("ui")
	public BeanFieldGroup<LoginModel> loginFieldGroup()
	{
		BeanFieldGroup<LoginModel> group = new BeanFieldGroup<LoginModel>(
				LoginModel.class);
		group.setItemDataSource(new LoginModel());
		group.setBuffered(false);
		return group;
	}

	@Bean
	@Scope("ui")
	public FormLayout loginForm(final BeanFieldGroup<LoginModel> loginFieldGroup)
	{
		TextField usernameField = loginFieldGroup.buildAndBind("username",
				"username", TextField.class);

		PasswordField passwordField = loginFieldGroup.buildAndBind("password",
				"password", PasswordField.class);

		Button submit = new Button("submit");
		submit.setClickShortcut(KeyCode.ENTER);

		final FormLayout layout = new FormLayout(usernameField, passwordField,
				submit);

		submit.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				layout.setEnabled(false);
				if (loginFieldGroup.isValid())
				{
					presenter.loginClicked();
				}
				else
				{
					presenter.loginFailure();
				}
				layout.setEnabled(true);
			}
		});
		return layout;
	}

	@Bean
	@Scope("ui")
	public Button logoutButton()
	{
		Button bean = new Button("Sign out", new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				presenter.logoutClicked();
			}
		});
		return bean;
	}
}