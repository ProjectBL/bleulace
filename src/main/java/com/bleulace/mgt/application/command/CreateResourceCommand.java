package com.bleulace.mgt.application.command;

import org.apache.shiro.SecurityUtils;
import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public abstract class CreateResourceCommand
{
	private final String creatorId = SecurityUtils.getSubject().getId();

	private final String id = IdentifierFactory.getInstance()
			.generateIdentifier();

	@NotEmpty
	private String title = "";
}
