package com.bleulace.domain.management.command;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.DomainEventPayload;

@RooJavaBean
public class RsvpCommand implements DomainEventPayload
{
	private final boolean accepted;

	public RsvpCommand(String id, boolean accepted)
	{
		setId(id);
		this.accepted = accepted;
	}

	public RsvpCommand(boolean accepted)
	{
		this(null, accepted);
	}
}
