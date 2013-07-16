package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AddTaskCommand
{
	@TargetAggregateIdentifier
	private final String bundleId;

	private final String taskId = IdentifierFactory.getInstance()
			.generateIdentifier();

	@NotEmpty
	private String title = "";

	public AddTaskCommand(String bundleId)
	{
		this.bundleId = bundleId;
	}
}