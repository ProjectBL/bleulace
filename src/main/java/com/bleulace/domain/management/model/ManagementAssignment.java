package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.resource.model.AbstractResource;

@Entity
@RooEquals(excludeFields = { "id", "title" })
@RooJavaBean(settersByDefault = false)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "MANAGER_ID",
		"RESOURCE_ID" }) })
public class ManagementAssignment extends AbstractResource
{
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, name = "MANAGER_ID")
	private Account account;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ManagementLevel role;

	public ManagementAssignment(Account account, ManagementLevel role)
	{
		this.account = account;
		this.role = role;
	}

	public ManagementAssignment()
	{
	}

	@Override
	public String getTitle()
	{
		return account.getTitle();
	}

	@Override
	protected void prePersist()
	{
		// do nothing
	}
}