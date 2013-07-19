package com.bleulace.mgt.application.command;

import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.infrastructure.ExecutingAccount;

@RooJavaBean
public abstract class CreateResourceCommand
{
	private final String creatorId = ExecutingAccount.id();

	private final String id = IdentifierFactory.getInstance()
			.generateIdentifier();

	@NotEmpty
	private String title = "";
}
