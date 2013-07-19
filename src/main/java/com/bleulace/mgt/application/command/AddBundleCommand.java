package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AddBundleCommand extends CreateResourceCommand
{
	@TargetAggregateIdentifier
	private final String parentId;

	public AddBundleCommand(String parentId)
	{
		this.parentId = parentId;
	}
}