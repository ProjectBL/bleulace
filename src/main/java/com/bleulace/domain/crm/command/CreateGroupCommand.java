package com.bleulace.domain.crm.command;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@RequiresAuthentication
public class CreateGroupCommand
{
	@NotEmpty
	private String title = "";
}