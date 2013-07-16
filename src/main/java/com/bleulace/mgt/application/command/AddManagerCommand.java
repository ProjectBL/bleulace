package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.mgt.domain.ManagementLevel;

@RooJavaBean
public class AddManagerCommand
{
	@TargetAggregateIdentifier
	private final String id;

	private String accountId;

	private ManagementLevel level;

	public AddManagerCommand(String id, String accountId, ManagementLevel level)
	{
		Assert.noNullElements(new Object[] { accountId, id, level });
		this.id = id;
		this.accountId = accountId;
		this.level = level;
	}
}