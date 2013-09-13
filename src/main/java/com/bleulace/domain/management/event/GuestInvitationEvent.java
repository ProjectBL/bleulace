package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.model.Event;

@RooJavaBean(settersByDefault = false)
public class GuestInvitationEvent
{
	private String accountId;

	private boolean invited;

	private transient Event source;

	public GuestInvitationEvent(Event source, String accountId, boolean invited)
	{
		this.source = source;
		this.accountId = accountId;
		this.invited = invited;
	}

	public Event getSource()
	{
		return source;
	}
}