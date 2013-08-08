package com.bleulace.crm.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class PostStatusUpdateCommand
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6932514705911770866L;

	private final String id = IdentifierFactory.getInstance()
			.generateIdentifier();

	@TargetAggregateIdentifier
	private final String accountId;

	@NotEmpty
	private String content;

	public PostStatusUpdateCommand(String accountId)
	{
		Assert.notNull(accountId);
		this.accountId = accountId;
	}

	public PostStatusUpdateCommand(String accountId, String content)
	{
		this(accountId);
		this.content = content;
	}
}