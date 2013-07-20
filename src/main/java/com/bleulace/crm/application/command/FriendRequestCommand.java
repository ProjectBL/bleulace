package com.bleulace.crm.application.command;

import org.apache.shiro.SecurityUtils;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

/**
 * @author Arleigh Dickerson
 * 
 */
@RooJavaBean(settersByDefault = false)
public abstract class FriendRequestCommand
{
	@TargetAggregateIdentifier
	private final String initiatorId;

	private final String recipientId;

	public FriendRequestCommand(String initiatorId, String recipientId)
	{
		Assert.noNullElements(new Object[] { initiatorId, recipientId });
		this.initiatorId = initiatorId;
		this.recipientId = recipientId;
	}

	public FriendRequestCommand(String recipientId)
	{
		this(SecurityUtils.getSubject().getId(), recipientId);
	}
}
