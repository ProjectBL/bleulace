package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateBundleCommand extends CreateProjectCommand
{
	public CreateBundleCommand(String projectId)
	{
		super(projectId);
	}

	@TargetAggregateIdentifier
	@Override
	public String getId()
	{
		return super.getId();
	}
}