package com.bleulace.domain.management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.equals.RooEquals;

import com.bleulace.domain.crm.model.Account;

@Embeddable
@RooEquals
public class Manager implements Serializable
{
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Account account;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ManagementRole role;

	public Manager(Account account, ManagementRole role)
	{
		this.account = account;
		this.role = role;
	}

	Manager()
	{
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public ManagementRole getRole()
	{
		return role;
	}

	public void setRole(ManagementRole role)
	{
		this.role = role;
	}
}
