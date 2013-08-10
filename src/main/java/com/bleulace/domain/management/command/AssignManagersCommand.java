package com.bleulace.domain.management.command;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.model.ManagementRole;

@RooJavaBean
public class AssignManagersCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotEmpty
	private Set<String> accountIds = new HashSet<String>();

	private ManagementRole role;

	public AssignManagersCommand(String id)
	{
		this.id = id;
	}
}
