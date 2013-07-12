package com.bleulace.crm.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * This event is used to track successful or unsuccessful login attempts.
 * 
 * @author Arleigh Dickerson
 * 
 */
@RooJavaBean(settersByDefault = false)
public class AccountLoginAttemptedEvent
{
	private static final long serialVersionUID = 5819278706713701979L;

	private Boolean success;

	public AccountLoginAttemptedEvent(Boolean success)
	{
		this.success = success;
	}
}