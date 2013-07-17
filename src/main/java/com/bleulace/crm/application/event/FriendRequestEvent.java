package com.bleulace.crm.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public abstract class FriendRequestEvent
{
	private static final long serialVersionUID = 4034201249807328008L;

	private String initiatorId;

	private String recipientId;

	public String getSagaId()
	{
		return initiatorId + ":" + recipientId;
	}
}
