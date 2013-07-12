package com.bleulace.accountRelations.application.command;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.accountRelations.infrastructure.Password;

@RooJavaBean
public class CreateAccountCommand
{
	@Email
	private String username;

	@Password
	private String password = "";

	@NotNull
	private String firstName = "";

	@NotNull
	private String lastName = "";
}