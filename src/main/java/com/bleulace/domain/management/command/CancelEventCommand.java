package com.bleulace.domain.management.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CancelEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	public CancelEventCommand(String id)
	{
		this.id = id;
	}
}
