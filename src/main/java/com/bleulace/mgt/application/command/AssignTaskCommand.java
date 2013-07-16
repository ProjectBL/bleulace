package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class AssignTaskCommand
{
	@TargetAggregateIdentifier
	private String taskId;

	private String accountId;
}
