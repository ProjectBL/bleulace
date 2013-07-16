package com.bleulace.mgt.application.command;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;
import com.bleulace.crm.infrastructure.ExecutingAccount;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RequiresAuthentication
@RooJavaBean
public class CreateProjectCommand
{
	private final String id;

	@NotEmpty
	private String title = "";

	private final Account creator = SpringApplicationContext.get()
			.getBean(ExecutingAccount.class).current();

	public CreateProjectCommand()
	{
		this(IdentifierFactory.getInstance().generateIdentifier());
	}

	public CreateProjectCommand(String id)
	{
		Assert.notNull(id);
		this.id = id;
	}
}