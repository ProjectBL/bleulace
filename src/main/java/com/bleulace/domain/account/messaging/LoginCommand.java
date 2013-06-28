package com.bleulace.domain.account.messaging;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class LoginCommand
{
	private String username;
	private String password;

	public LoginCommand(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
}
