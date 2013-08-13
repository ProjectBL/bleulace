package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.EventSourcedEntityMixin;
import com.bleulace.domain.crm.model.Account;

@Embeddable
@RooJavaBean(settersByDefault = false)
public class ManagementAssignment implements EventSourcedEntityMixin
{
	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, name = "MANAGER_ID")
	private Account account;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ManagementRole role;

	private ManagementAssignment(Account account, ManagementRole role)
	{
		this.account = account;
		this.role = role;
	}

	private ManagementAssignment()
	{
	}

	@Override
	public String getId()
	{
		return null;
	}
}
