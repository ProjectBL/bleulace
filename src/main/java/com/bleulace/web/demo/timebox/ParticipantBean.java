package com.bleulace.web.demo.timebox;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.jpa.EntityManagerReference;

@RooJavaBean
@RooSerializable
class ParticipantBean
{
	@NotNull
	private String id;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String email;

	private RsvpStatus status;

	ParticipantBean(Account account, RsvpStatus status)
	{
		this.id = account.getId();
		this.firstName = account.getContactInformation().getFirstName();
		this.lastName = account.getContactInformation().getLastName();
		this.email = account.getContactInformation().getEmail();
		this.status = status;
	}

	ParticipantBean(Account account)
	{
		this(account, null);
	}

	Account getAccount()
	{
		return EntityManagerReference.load(Account.class, id);
	}

	public String getName()
	{
		return firstName + " " + lastName;
	}
}