package com.bleulace.domain.crm.command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CreateAccountCommand
{
	@NotEmpty
	private String username = "";

	@NotEmpty
	private String password = "";

	public CreateAccountCommand()
	{
	}

	public CreateAccountCommand(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
}
