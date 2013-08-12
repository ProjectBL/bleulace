package com.bleulace.domain.management.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class MarkTaskCommand
{
	@TargetAggregateIdentifier
	private final String id;

	private final boolean complete;

	public MarkTaskCommand(String id, Boolean complete)
	{
		this.id = id;
		this.complete = complete;
	}
}
