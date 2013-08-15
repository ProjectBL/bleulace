package com.bleulace.domain.management.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CreateTaskCommand extends CreateManageableResourceCommand
{
	@TargetAggregateIdentifier
	private final String id;

	public CreateTaskCommand(String id)
	{
		this.id = id;
	}
}