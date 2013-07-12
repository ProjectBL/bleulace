package com.bleulace.accountRelations.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.accountRelations.infrastructure.Password;

@RooJavaBean(settersByDefault = false)
public class ChangePasswordCommand
{
	@TargetAggregateIdentifier
	private String id;

	@Password
	private String value = "";

	public ChangePasswordCommand()
	{
	}

	public ChangePasswordCommand(String id)
	{
		this.id = id;
	}
}