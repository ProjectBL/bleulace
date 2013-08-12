package com.bleulace.domain.management.command;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.DomainEventPayload;

@RooJavaBean
public class RsvpCommand implements DomainEventPayload
{
	@NotNull
	private final String accountId;

	private final boolean accepted;

	public RsvpCommand(String id, String accountId, boolean accepted)
	{
		setId(id);
		this.accountId = accountId;
		this.accepted = accepted;
	}
}
