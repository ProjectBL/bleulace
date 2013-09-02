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
	private String username = "";

	@NotEmpty
	private String firstName = "";

	@NotEmpty
	private String lastName = "";

	@NotEmpty
	private String school = "";

	@NotEmpty
	private String work = "";

	@NotEmpty
	private String location = "";

	public String getName()
	{
		return firstName + " " + lastName;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof UserDTO)
		{
			UserDTO that = (UserDTO) obj;
			if (this.getId() == null)
			{
				return this == that;
			}
			return this.getId().equals(that.getId());
		}
		return false;
	}
}