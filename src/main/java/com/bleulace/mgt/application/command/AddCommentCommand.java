package com.bleulace.mgt.application.command;

import org.apache.shiro.SecurityUtils;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AddCommentCommand
{
	@TargetAggregateIdentifier
	private final String id;

	private final String accountId = SecurityUtils.getSubject().getId();

	@NotEmpty
	private String content = "";

	public AddCommentCommand(String id)
	{
		this.id = id;
	}
}