package com.bleulace.mgt.application.command;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RequiresAuthentication
@RooJavaBean
public class CreateProjectCommand
{
	private final String id;

	@NotEmpty
	private String title = "";

	private final String creatorId = (String) SecurityUtils.getSubject()
			.getPrincipal();

	public CreateProjectCommand(String id)
	{
		Assert.notNull(id);
		this.id = id;
	}
}