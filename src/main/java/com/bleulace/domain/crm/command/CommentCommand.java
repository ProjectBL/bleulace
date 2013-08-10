package com.bleulace.domain.crm.command;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RequiresAuthentication
@RooJavaBean
public class CommentCommand
{
	@TargetAggregateIdentifier
	private String id;

	@NotEmpty
	private String content = "";

	public CommentCommand(String id)
	{
		this.id = id;
	}
}