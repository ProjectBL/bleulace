package com.bleulace.domain.crm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Embeddable
@RooEquals
@RooToString
@RooJavaBean(settersByDefault = false)
public class ContactInformation implements Serializable
{
	private String firstName;
	private String lastName;
	private String email;
	private String school;
	private String work;

	public ContactInformation(String firstName, String lastName, String email,
			String school, String work)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.school = school;
		this.work = work;
	}

	private ContactInformation()
	{
	}

	public static ContactInformation defaultValues()
	{
		return new ContactInformation("", "", "", "", "");
	}
}