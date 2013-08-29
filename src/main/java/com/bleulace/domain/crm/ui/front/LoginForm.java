package com.bleulace.domain.crm.ui.front;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
@Scope("prototype")
public class LoginForm extends CustomComponent implements ClickListener,
		CommandGatewayAware
{
	@Autowired
	@Qualifier("uiBus")
	private transient EventBus uiBus;

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

	@PostConstruct
	void clearValues()
	{
		usernameField.setValue("");
		passwordField.setValue("");
		rememberMeField.setValue(false);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		uiBus.publish(GenericEventMessage
				.asEventMessage(new UsernamePasswordToken(usernameField
						.getValue(), passwordField.getValue(), rememberMeField
						.getValue())));
	}
}