package com.bleulace.domain.account.messaging;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class LoginAttemptedEvent
{
	private String username;
	private boolean success;

	public LoginAttemptedEvent(String username, boolean success)
	{
		this.username = username;
		this.success = success;
	}
}
