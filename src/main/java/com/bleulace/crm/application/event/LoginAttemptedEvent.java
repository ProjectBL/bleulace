package com.bleulace.crm.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * This event is used to track successful or unsuccessful login attempts.
 * 
 * @author Arleigh Dickerson
 * 
 */
@RooToString
@RooJavaBean(settersByDefault = false)
public class LoginAttemptedEvent
{
	private static final long serialVersionUID = 5819278706713701979L;

	private String username;

	private Boolean success;

	public LoginAttemptedEvent(String username, Boolean success)
	{
		this.success = success;
		this.username = username;
	}
}