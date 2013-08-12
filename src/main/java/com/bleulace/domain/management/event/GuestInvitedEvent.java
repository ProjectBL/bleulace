package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class GuestInvitedEvent
{
	private String id;

	private String accountId;

	public GuestInvitedEvent(String id, String accountId)
	{
		this.id = id;
		this.accountId = accountId;
	}
}
