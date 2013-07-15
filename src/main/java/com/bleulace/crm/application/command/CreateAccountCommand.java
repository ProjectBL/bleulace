package com.bleulace.crm.application.command;

import org.axonframework.domain.IdentifierFactory;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.Assert;

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
	private final String id;

	public CreateAccountCommand()
	{
		this(IdentifierFactory.getInstance().generateIdentifier());
	}

	public CreateAccountCommand(String id)
	{
		Assert.notNull(id);
		this.id = id;
	}

	@Email
	@NotEmpty
	private String email = "";

	@Password
	private String password = "";

	@NotEmpty
	private String firstName = "";

	@NotEmpty
	private String lastName = "";

	public String getId()
	{
		return id;
	}
}