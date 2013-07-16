package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class AddBundleCommand extends CreateProjectCommand
{
	@TargetAggregateIdentifier
	private final String parentId;

	public AddBundleCommand(String id)
	{
		super();
		Assert.notNull(id);
		this.parentId = id;
	}
}