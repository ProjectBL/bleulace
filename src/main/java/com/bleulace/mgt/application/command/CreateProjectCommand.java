package com.bleulace.mgt.application.command;

import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RequiresAuthentication
@RooJavaBean(settersByDefault = false)
public class CreateProjectCommand
{
	private String id;

	@NotEmpty
	private String title = "";

	@NotNull
	private final String creatorId;

	public CreateProjectCommand(String id, String creatorId)
	{
		Assert.noNullElements(new Object[] { id, creatorId });
		this.id = id;
		this.creatorId = creatorId;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}