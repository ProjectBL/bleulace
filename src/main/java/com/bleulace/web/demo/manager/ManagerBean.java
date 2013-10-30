package com.bleulace.web.demo.manager;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.ManagementLevel;

@RooJavaBean
class ManagerBean
{
	@NotNull
	private String id;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String email;

	@NotNull
	private ManagementLevel level;

	public ManagerBean(Account account, ManagementLevel level)
	{
		this.id = account.getId();
		this.firstName = account.getContactInformation().getFirstName();
		this.lastName = account.getContactInformation().getLastName();
		this.email = account.getContactInformation().getEmail();
		this.level = level;
	}

	public ManagerBean(Account account)
	{
		this(account, ManagementLevel.LOOP);
	}

	public String getName()
	{
		return firstName + " " + lastName;
	}
}