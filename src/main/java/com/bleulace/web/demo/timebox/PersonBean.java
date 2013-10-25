package com.bleulace.web.demo.timebox;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooJavaBean
@RooSerializable
public class PersonBean
{
	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	public PersonBean()
	{
		this("", "");
	}

	public PersonBean(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString()
	{
		return firstName + " " + lastName;
	}
}