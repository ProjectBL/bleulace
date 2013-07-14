package com.bleulace.mgt.application.command;

import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class CreateTaskCommand
{
	@AggregateIdentifier
	private final String bundleId;

	private final String taskId = IdentifierFactory.getInstance()
			.generateIdentifier();

	private String title;

	public CreateTaskCommand(String bundleId)
	{
		this.bundleId = bundleId;
	}

	public String getBundleId()
	{
		return bundleId;
	}

	public String getTitle()
	{
		return title;
	}

	public String getTaskId()
	{
		return taskId;
	}
}