package com.bleulace.domain.management.command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class InviteGuestsCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotEmpty
	private final Set<String> accountIds = new HashSet<String>();

	public InviteGuestsCommand(String id)
	{
		this.id = id;
	}

	public InviteGuestsCommand(String id, String... accountIds)
	{
		this(id);
		this.accountIds.addAll(Arrays.asList(accountIds));
	}

}
