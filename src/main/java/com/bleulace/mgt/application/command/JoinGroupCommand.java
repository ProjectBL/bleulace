package com.bleulace.mgt.application.command;

import org.apache.shiro.SecurityUtils;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class JoinGroupCommand
{
	@TargetAggregateIdentifier
	private String groupId;

	private String accountId;

	public JoinGroupCommand(String groupId)
	{
		this(groupId, (String) SecurityUtils.getSubject().getPrincipal());
	}

	public JoinGroupCommand(String groupId, String accountId)
	{
		Assert.noNullElements(new Object[] { groupId, accountId });
		this.accountId = accountId;
		this.groupId = groupId;
	}

	public JoinGroupCommand()
	{
	}
}