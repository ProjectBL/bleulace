package com.bleulace.domain.management.command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.model.ManagementLevel;

@RooJavaBean
public class AssignManagersCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotEmpty
	private Set<String> accountIds = new HashSet<String>();

	private ManagementLevel role;

	public AssignManagersCommand(String id, ManagementLevel role,
			String... ids)
	{
		this.id = id;
		this.role = role;
		accountIds.addAll(Arrays.asList(ids));
	}
}