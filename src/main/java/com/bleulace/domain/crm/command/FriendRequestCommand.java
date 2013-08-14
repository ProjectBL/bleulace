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
	private final String targetAccountId;

	@NotNull
	private FriendRequestAction action;

	public FriendRequestCommand(String targetAccountId,
			FriendRequestAction action)
	{
		Assert.noNullElements(new Object[] { targetAccountId, action });
		this.targetAccountId = targetAccountId;
		this.action = action;
	}

	public String getId()
	{
		return targetAccountId;
	}
}