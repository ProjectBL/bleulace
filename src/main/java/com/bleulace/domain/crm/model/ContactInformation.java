package com.bleulace.domain.crm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Embeddable
@RooEquals
@RooToString
@RooJavaBean
public class ContactInformation implements Serializable
{
	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@Email
	private String email;

	@NotNull
	private String school;

	@NotNull
	private String work;

	@NotNull
	private String location;

	public ContactInformation(String firstName, String lastName, String email,
			String school, String work, String location)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.school = school;
		this.work = work;
		this.location = location;
	}

	public ContactInformation()
	{
		this("", "", "", "", "", "");
	}
}