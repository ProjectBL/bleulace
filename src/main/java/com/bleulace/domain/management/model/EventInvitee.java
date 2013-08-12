package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.cqrs.EventSourcedEntityMixin;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.command.RsvpCommand;

@RooJavaBean(settersByDefault = false)
@Embeddable
public class EventInvitee implements EventSourcedEntityMixin
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

	private EventInvitee()
	{
	}

	public EventInvitee(Account guest, Account host)
	{
		this.host = host;
		this.guest = guest;
		status = RsvpStatus.PENDING;
	}

	public void on(RsvpCommand event)
	{
		if (guest.getId().equals(event.getAccountId()))
		{
			status = event.isAccepted() ? RsvpStatus.ACCEPTED
					: RsvpStatus.DECLINED;
		}
	}

	@Override
	public String getId()
	{
		return null;
	}
}
