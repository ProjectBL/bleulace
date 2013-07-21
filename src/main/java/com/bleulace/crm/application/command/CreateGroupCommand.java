package com.bleulace.crm.application.command;

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

	@NotEmpty
	private String title = "";
}