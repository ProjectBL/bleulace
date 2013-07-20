package com.bleulace.crm.application.command;

import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class JoinGroupCommand
{
	@TargetAggregateIdentifier
	private final String groupId;

	@NotNull
	private final String accountId;

	public JoinGroupCommand(String groupId)
	{
		this(groupId, SecurityUtils.getSubject().getId());
	}

	public JoinGroupCommand(String groupId, String accountId)
	{
		Assert.noNullElements(new Object[] { groupId, accountId });
		this.accountId = accountId;
		this.groupId = groupId;
	}
}