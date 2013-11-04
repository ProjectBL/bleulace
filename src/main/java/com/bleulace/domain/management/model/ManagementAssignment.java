package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.equals.RooEquals;

import com.bleulace.domain.crm.model.Account;

@Embeddable
@RooEquals
public class ManagementAssignment
{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, updatable = false)
	private Account account;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ManagementLevel level;

	public ManagementAssignment(Account account, ManagementLevel level)
	{
		this.account = account;
		this.level = level;
	}

	public Account getAccount()
	{
		return account;
	}

	public ManagementLevel getLevel()
	{
		return level;
	}

	public void setLevel(ManagementLevel level)
	{
		this.level = level;
	}

	@SuppressWarnings("unused")
	private ManagementAssignment()
	{
	}
}