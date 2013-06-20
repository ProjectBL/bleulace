package com.bleulace.ui.mobile;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.vaadin.addon.touchkit.gwt.client.theme.StyleNames;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends NavigationView implements ClickListener
{
	private static final long serialVersionUID = -7778509423006309766L;

	private BeanFieldGroup<UsernamePasswordToken> fieldGroup = new BeanFieldGroup<UsernamePasswordToken>(
			UsernamePasswordToken.class);

	private TextField username;
	private PasswordField password;
	private CheckBox rememberMe;
	private Button login;
	private Button register;

	private Component[] components;

	public LoginView()
	{
		fieldGroup.setBuffered(false);
		fieldGroup.setItemDataSource(new UsernamePasswordToken());

		username = fieldGroup.buildAndBind("Username", "username",
				TextField.class);
		password = fieldGroup.buildAndBind("Password", "password",
				PasswordField.class);
		rememberMe = fieldGroup.buildAndBind("Remember Me", "rememberMe",
				CheckBox.class);

		login = new Button("Login", this);
		login.setSizeFull();
		register = new Button("Register");
		register.addStyleName(StyleNames.BUTTON_BLUE);
		register.setSizeFull();

		components = new Component[] { username, password, rememberMe, login,
				register };

		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(new Label("<h1>BlueLace</h1>", ContentMode.HTML));

		VerticalComponentGroup group = new VerticalComponentGroup();
		group.addComponents(username, password, rememberMe);
		layout.addComponents(group, login, register);
		setContent(layout);
	}

	public void setEnabled(Boolean enabled)
	{
		super.setEnabled(enabled);
		for (Component c : components)
		{
			c.setEnabled(enabled);
		}
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		setEnabled(false);
		try
		{
			SecurityUtils.getSubject().login(
					fieldGroup.getItemDataSource().getBean());
			Notification.show("Login success");
		}
		catch (AuthenticationException e)
		{
			Notification.show("Login failed");
		}
		finally
		{
			fieldGroup.setItemDataSource(new UsernamePasswordToken());
			setEnabled(true);
		}
	}
}