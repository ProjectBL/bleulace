package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean(settersByDefault = false)
public abstract class AssignmentCommand<T extends Enum<T>>
{
	@TargetAggregateIdentifier
	private final String id;

	private final String accountId;

	private final T role;

	public AssignmentCommand(String id, String accountId, T role)
	{
		Assert.noNullElements(new Object[] { accountId, id });
		this.id = id;
		this.accountId = accountId;
		this.role = role;
	}
}
