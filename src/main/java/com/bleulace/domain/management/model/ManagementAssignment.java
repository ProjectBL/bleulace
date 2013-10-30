package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.resource.model.AbstractResource;

@Entity
@RooEquals
@RooJavaBean(settersByDefault = false)
public class ManagementAssignment
{
	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, name = "MANAGER_ID")
	private Account account;

	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, name = "RESOURCE_ID")
	private AbstractResource resource;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ManagementLevel role;

	public ManagementAssignment(Account account, AbstractResource resource,
			ManagementLevel role)
	{
		this.account = account;
		this.resource = resource;
		this.role = role;
	}

	public void setRole(ManagementLevel role)
	{
		this.role = role;
	}

	@SuppressWarnings("unused")
	private ManagementAssignment()
	{
	}
}