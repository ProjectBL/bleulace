package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class RsvpCommand
{
	@TargetAggregateIdentifier
	private final String id;

	private final String guestId;

	private boolean accepted;

	public RsvpCommand(String id, String guestId, boolean accepted)
	{
		this.id = id;
		this.guestId = guestId;
		this.accepted = accepted;
	}

	public RsvpCommand(String id, String guestId)
	{
		this(id, guestId, true);
	}
}
