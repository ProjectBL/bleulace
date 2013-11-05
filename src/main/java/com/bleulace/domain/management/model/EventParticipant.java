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
public class EventParticipant implements Serializable
{
	@JoinColumn(nullable = false, updatable = false)
	@ManyToOne
	private Account account;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RsvpStatus status;

	public EventParticipant(Account account, RsvpStatus status)
	{
		this.account = account;
		this.status = status;
	}

	public Account getAccount()
	{
		return account;
	}

	public RsvpStatus getStatus()
	{
		return status;
	}

	public void setStatus(RsvpStatus status)
	{
		this.status = status;
	}

	EventParticipant()
	{
	}
}