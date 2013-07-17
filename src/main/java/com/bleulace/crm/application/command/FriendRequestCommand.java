package com.bleulace.crm.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public abstract class FriendRequestCommand
{
	@TargetAggregateIdentifier
	private String initiatorId;

	private String recipientId;

	public FriendRequestCommand(String initiatorId, String recipientId)
	{
		this.initiatorId = initiatorId;
		this.recipientId = recipientId;
	}
}
