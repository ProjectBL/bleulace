package com.bleulace.crm.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class SendFriendRequestCommand
{
	@TargetAggregateIdentifier
	private String initiatorId;

	private String recipientId;

	public SendFriendRequestCommand(String initiatorId, String recipientId)
	{
		this.initiatorId = initiatorId;
		this.recipientId = recipientId;
	}
}
