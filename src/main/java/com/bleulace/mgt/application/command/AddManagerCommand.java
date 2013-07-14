package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.util.Assert;

import com.bleulace.mgt.domain.ManagementLevel;

public class AddManagerCommand
{
	@TargetAggregateIdentifier
	private final String projectId;

	private final String targetId;

	private final String accountId;

	private final ManagementLevel level;

	public AddManagerCommand(String projectId, String targetId,
			String accountId, ManagementLevel level)
	{
		Assert.noNullElements(new Object[] { accountId, projectId, targetId,
				level });
		this.projectId = projectId;
		this.targetId = targetId;
		this.accountId = accountId;
		this.level = level;
	}

	public AddManagerCommand(String projectId, String accountId,
			ManagementLevel level)
	{
		this(projectId, projectId, accountId, level);
	}

	public String getAccountId()
	{
		return accountId;
	}

	public String getTargetId()
	{
		return targetId;
	}

	public ManagementLevel getLevel()
	{
		return level;
	}
}