package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;

@RooEquals
@RooJavaBean(settersByDefault = false)
@Embeddable
public class EventInvitee
{
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Account account;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RsvpStatus status;

	public EventInvitee(Account account, RsvpStatus status)
	{
		this.account = account;
		this.status = status;
	}

	public void setStatus(RsvpStatus status)
	{
		this.status = status;
	}

	@SuppressWarnings("unused")
	private EventInvitee()
	{
	}
}