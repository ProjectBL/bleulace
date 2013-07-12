package com.bleulace.accountRelations.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AccountInfoUpdatedEvent
{
	private String username;
	private String firstName;
	private String lastName;
}
