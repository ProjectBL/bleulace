package com.bleulace.mgt.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;
import com.bleulace.persistence.infrastructure.QueryFactory;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "ACCOUNT_ID",
		"EVENT_ID" }) })
public class EventInvitation extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = -8782392444912650040L;

	@JoinColumn(nullable = false, updatable = false, name = "ACCOUNT_ID")
	private Account account;

	@JoinColumn(nullable = false, updatable = false, name = "EVENT_ID")
	private Event event;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	EventInvitation(Account invitee, Event event)
	{
		Assert.noNullElements(new Object[] { invitee, event });
		this.account = invitee;
		this.event = event;
	}

	public Account getAccount()
	{
		return account;
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
				.where(e.account.id.eq(accountId).and(e.event.id.eq(eventId)))
				.singleResult(e);
	}

	public static void delete(String accountId, String eventId)
	{
		EntityManagerReference.get().remove(find(accountId, eventId));
	}
}