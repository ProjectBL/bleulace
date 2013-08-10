package com.bleulace.domain.crm.command;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.GroupMembershipAction;

@RequiresAuthentication
@RooJavaBean
public class GroupMembershipCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotEmpty
	private Set<String> accountIds = new HashSet<String>();

	private final GroupMembershipAction action;

	public GroupMembershipCommand(String id, GroupMembershipAction action)
	{
		this.id = id;
		this.action = action;
	}
}