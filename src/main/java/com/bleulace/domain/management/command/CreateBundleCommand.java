package com.bleulace.domain.management.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CreateBundleCommand extends CreateMgtResourceCommand
{
	@TargetAggregateIdentifier
	private final String id;

	public CreateBundleCommand(String id)
	{
		this.id = id;
	}
}