package com.bleulace.domain.crm.command;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateGroupCommand
{
	@NotEmpty
	private String title = "";

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}