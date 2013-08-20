package com.bleulace.domain.crm.ui;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
public class LoginForm extends CustomComponent implements ClickListener,
		CommandGatewayAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3155867696094372161L;

	private static final String USERNAME_FIELD_CAPTION = "Username";
	private static final String PASSWORD_FIELD_CAPTION = "Password";
	private static final String REMEMBER_ME_FIELD_CAPTION = "Remember Me";
	private static final String SUBMIT_BUTTON_CAPTION = "Log in";

	private final TextField usernameField = new TextField(
			USERNAME_FIELD_CAPTION);

	private final PasswordField passwordField = new PasswordField(
			PASSWORD_FIELD_CAPTION);

	private final CheckBox rememberMeField = new CheckBox(
			REMEMBER_ME_FIELD_CAPTION);

	private final Button submitButton = new Button(SUBMIT_BUTTON_CAPTION, this);

	public LoginForm()
	{
		submitButton.setClickShortcut(KeyCode.ENTER);
		setCompositionRoot(new FormLayout(usernameField, passwordField,
				rememberMeField, submitButton));
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		try
		{
			SecurityUtils.getSubject().login(usernameField.getValue(),
					passwordField.getValue(), rememberMeField.getValue());
			getUI().getNavigator().navigateTo(
					"calendarView/" + SecurityUtils.getSubject().getId());
		}
		catch (AuthenticationException e)
		{
			Notification.show("Authentication Failure");
		}
	}
}