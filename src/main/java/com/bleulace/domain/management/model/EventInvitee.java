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
	@JoinColumn(nullable = false, updatable = false, name = "GUEST_ID")
	private Account guest;

	@ManyToOne
	@JoinColumn(updatable = false, name = "HOST_ID")
	private Account host;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RsvpStatus status;

	public EventInvitee(Account guest, Account host, RsvpStatus status)
	{
		this.host = host;
		this.guest = guest;
		this.status = status;
	}

	public EventInvitee(Account guest, Account host)
	{
		this(guest, host, RsvpStatus.PENDING);
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