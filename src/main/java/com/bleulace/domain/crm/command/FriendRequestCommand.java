package com.bleulace.domain.crm.command;

import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.FriendRequestAction;

/**
 * @author Arleigh Dickerson
 * 
 */
@RequiresAuthentication
@RooJavaBean(settersByDefault = false)
public class FriendRequestCommand
{
	@TargetAggregateIdentifier
	private final String initiatorId;

	@NotNull
	private final String recipientId;

	@NotNull
	private FriendRequestAction action;

	public FriendRequestCommand(String initiatorId, String recipientId,
			FriendRequestAction action)
	{
		Assert.noNullElements(new Object[] { initiatorId, recipientId });
		this.initiatorId = initiatorId;
		this.recipientId = recipientId;
		this.action = action;
	}

	public String getId()
	{
		return initiatorId;
	}
}