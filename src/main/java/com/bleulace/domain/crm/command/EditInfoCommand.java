package com.bleulace.domain.crm.command;

import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.ContactInformation;
import com.bleulace.domain.crm.model.Password;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.dto.Mapper;

@RooJavaBean
public class EditInfoCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@Email
	@NotEmpty
	private String email;

	@NotNull
	private String school;

	@NotNull
	private String work;

	@Password
	private String password;

	EditInfoCommand(String id)
	{
		this.id = id;
		setToPersistentValues();
	}

	public ContactInformation getInformation()
	{
		return new ContactInformation(firstName, lastName, email, school, work);
	}

	public void setToPersistentValues()
	{
		Mapper.map(persistentValues(), this);
		password = null;
	}

	private ContactInformation persistentValues()
	{
		return EntityManagerReference.load(Account.class, id)
				.getContactInformation();
	}
}