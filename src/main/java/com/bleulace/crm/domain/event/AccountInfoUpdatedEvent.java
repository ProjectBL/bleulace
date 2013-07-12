package com.bleulace.crm.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;

/**
 * This event is applied to {@link Account} instances to update personal info.
 * 
 * @author Arleigh Dickerson
 * 
 */
@RooJavaBean
public class AccountInfoUpdatedEvent
{
	private static final long serialVersionUID = -8717341530583207856L;

	private String email;
	private String firstName;
	private String lastName;
}