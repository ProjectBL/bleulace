package com.bleulace.crm.application.command;

import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.crm.infrastructure.ExecutingAccount;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RooJavaBean
public class CreateGroupCommand
{
	private final String id = IdentifierFactory.getInstance()
			.generateIdentifier();

	private final Account creator = SpringApplicationContext.get()
			.getBean(ExecutingAccount.class).current();

	@NotEmpty
	private String title = "";
}
