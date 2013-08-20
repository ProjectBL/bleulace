package com.bleulace.domain.crm.presentation;

import java.util.TimeZone;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class UserDTO
{
	@NotNull
	private String id;

	private byte[] image;

	private TimeZone timeZone;

	@NotEmpty
	private String username;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	public String getFullName()
	{
		return firstName + " " + lastName;
	}
}