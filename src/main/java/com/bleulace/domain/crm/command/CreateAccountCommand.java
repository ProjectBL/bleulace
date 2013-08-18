package com.bleulace.domain.crm.command;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.ContactInformation;

@RooJavaBean
public class CreateAccountCommand
{
	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	@Valid
	@NotNull
	private final ContactInformation contactInformation;

	public CreateAccountCommand()
	{
		this("", "");
	}

	public CreateAccountCommand(String username, String password,
			ContactInformation information)
	{
		this.username = username;
		this.password = password;
		this.contactInformation = information;
	}

	public CreateAccountCommand(String username, String password)
	{
		this(username, password, new ContactInformation());
	}

	public String getId()
	{
		return null;
	}

	public void setFirstName(String firstName)
	{
		contactInformation.setFirstName(firstName);
	}

	public void setLastName(String lastName)
	{
		contactInformation.setLastName(lastName);
	}
}