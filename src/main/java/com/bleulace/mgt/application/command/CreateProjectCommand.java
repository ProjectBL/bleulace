package com.bleulace.mgt.application.command;

import javax.validation.constraints.NotNull;

import junit.framework.Assert;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RequiresAuthentication
@RooJavaBean(settersByDefault = false)
public class CreateProjectCommand
{
	@NotEmpty
	private String name = "";

	@NotNull
	private final String creatorId;

	public CreateProjectCommand()
	{
		this(SecurityUtils.getSubject().getPrincipal().toString());
	}

	public CreateProjectCommand(String creatorId)
	{
		Assert.assertNotNull(creatorId);
		this.creatorId = creatorId;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}