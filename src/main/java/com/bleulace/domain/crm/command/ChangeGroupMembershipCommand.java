package com.bleulace.domain.crm.command;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class ChangeGroupMembershipCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotEmpty
	private Set<String> accountIds = new HashSet<String>();

	private final boolean joining;

	public ChangeGroupMembershipCommand(String id, boolean joining)
	{
		this.id = id;
		this.joining = joining;
	}
}