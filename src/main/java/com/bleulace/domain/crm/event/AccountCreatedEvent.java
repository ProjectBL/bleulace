package com.bleulace.domain.crm.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AccountCreatedEvent
{
	private String username;

	private String password;
}