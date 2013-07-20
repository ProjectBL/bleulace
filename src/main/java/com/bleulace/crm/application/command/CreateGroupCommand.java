package com.bleulace.crm.application.command;

import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * A command to create a group.
 * 
 * @author Arleigh Dickerson
 * 
 */
@RooJavaBean
public class CreateGroupCommand
{
	private final String id = IdentifierFactory.getInstance()
			.generateIdentifier();

	@NotNull
	private final String creatorId = SecurityUtils.getSubject().getId();

	@NotEmpty
	private String title = "";
}