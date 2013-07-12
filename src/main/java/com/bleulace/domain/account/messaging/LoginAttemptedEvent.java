package com.bleulace.domain.account.messaging;

import java.io.Serializable;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class LoginAttemptedEvent implements Serializable
{
	private static final long serialVersionUID = 6808442284658345041L;

	private String username;
	private boolean success;

	public LoginAttemptedEvent()
	{
	}

	public LoginAttemptedEvent(String username, boolean success)
	{
		this.username = username;
		this.success = success;
	}
}