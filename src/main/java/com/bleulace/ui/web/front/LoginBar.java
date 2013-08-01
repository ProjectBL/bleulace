package com.bleulace.ui.web.front;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.LoginCommand;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class LoginBar extends CustomComponent implements ClickListener,
		CommandGatewayAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3155867696094372161L;

	private final BeanItem<LoginCommand> item = new BeanItem<LoginCommand>(
			new LoginCommand());

	public LoginBar()
	{
		FieldGroup fieldGroup = new FieldGroup(item);
		//@formatter:off
		GridLayout layout = new GridLayout(4, 1,
		fieldGroup.buildAndBind("username","username", TextField.class),
		fieldGroup.buildAndBind("password", "password",PasswordField.class),
		fieldGroup.buildAndBind("rememberMe","rememberMe", CheckBox.class),
		new Button("Log In",this));
		//@formatter:on
		setCompositionRoot(layout);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		System.out.println(item.getBean().getUsername());
	}
}