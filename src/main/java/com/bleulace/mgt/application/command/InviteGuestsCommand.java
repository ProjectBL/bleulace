package com.bleulace.mgt.application.command;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean(settersByDefault = false)
public class InviteGuestsCommand
{
	@TargetAggregateIdentifier
	private final String id;

	private Set<String> guestIds = new HashSet<String>();

	public InviteGuestsCommand(String id)
	{
		Assert.notNull(id);
		this.id = id;
	}
}
