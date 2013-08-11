package com.bleulace.domain.management.command;

import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class RsvpCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotNull
	private final String accountId;

	private final boolean accepted;

	public RsvpCommand(String id, String accountId, boolean accepted)
	{
		this.id = id;
		this.accountId = accountId;
		this.accepted = accepted;
	}
}
