package com.bleulace.mgt.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;
import com.bleulace.persistence.infrastructure.QueryFactory;

@Entity
public class EventInvitation implements Serializable
{
	private static final long serialVersionUID = -8782392444912650040L;

	@Id
	@JoinColumn(nullable = false, updatable = false)
	private Account invitee;

	@Id
	@JoinColumn(nullable = false, updatable = false)
	private Event event;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	EventInvitation(Account invitee, Event event)
	{
		Assert.noNullElements(new Object[] { invitee, event });
		this.invitee = invitee;
		this.event = event;
	}

	public Account getInvitee()
	{
		return invitee;
	}

	public Event getEvent()
	{
		return event;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	@SuppressWarnings("unused")
	private EventInvitation()
	{
	}

	public static EventInvitation find(String accountId, String eventId)
	{
		QEventInvitation e = QEventInvitation.eventInvitation;
		return QueryFactory.from(e)
				.where(e.invitee.id.eq(accountId).and(e.event.id.eq(eventId)))
				.singleResult(e);
	}

	public static void delete(String accountId, String eventId)
	{
		QEventInvitation e = QEventInvitation.eventInvitation;
		QueryFactory.delete(e).where(
				e.invitee.id.eq(accountId).and(e.event.id.eq(eventId)));
	}
}