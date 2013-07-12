package com.bleulace.crm.application.command;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.crm.infrastructure.Password;

/**
 * A command to create a new account.
 * 
 * After the gateway processes this command, the account will be ready to use by
 * the client.
 * 
 * @author Arleigh Dickerson
 * 
 */
@RooToString
@RooJavaBean
public class CreateAccountCommand
{
	@Email
	private String email;

	@Password
	private String password = "";

	@NotEmpty
	private String firstName = "";

	@NotEmpty
	private String lastName = "";
}