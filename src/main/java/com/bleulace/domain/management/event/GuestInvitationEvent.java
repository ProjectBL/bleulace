package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class GuestInvitationEvent
{
	private String id;

	private String accountId;

	private boolean invited;

	public GuestInvitationEvent(String id, String accountId, boolean invited)
	{
		this.id = id;
		this.accountId = accountId;
		this.invited = invited;
	}
}
