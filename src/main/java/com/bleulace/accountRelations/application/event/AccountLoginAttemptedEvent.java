package com.bleulace.accountRelations.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class AccountLoginAttemptedEvent
{
	private Boolean success;

	public AccountLoginAttemptedEvent(Boolean success)
	{
		this.success = success;
	}
}