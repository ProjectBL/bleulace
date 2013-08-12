package com.bleulace.domain.crm.command;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.DomainEventPayload;

@RequiresAuthentication
@RooJavaBean
public class CommentCommand implements DomainEventPayload
{
	@NotEmpty
	private String content = "";

	public CommentCommand(String id)
	{
		setId(id);
	}
}