package com.bleulace.domain.crm.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.ContactInformation;

@RooJavaBean
public class AccountCreatedEvent
{
	private String username;

	private String password;

	private ContactInformation contactInformation;
}