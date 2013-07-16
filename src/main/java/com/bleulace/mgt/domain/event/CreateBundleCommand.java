package com.bleulace.mgt.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CreateBundleCommand
{
	@TargetAggregateIdentifier
	private final String projectId;

	@NotEmpty
	private String title = "";

	public CreateBundleCommand(String projectId)
	{
		this.projectId = projectId;
	}
}
