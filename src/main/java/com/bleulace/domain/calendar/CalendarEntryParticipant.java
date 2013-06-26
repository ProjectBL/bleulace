package com.bleulace.domain.calendar;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.account.Account;

@Entity
@RooJavaBean
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "ACCOUNT_ID",
		"ENTRY_ID" }) })
public class CalendarEntryParticipant implements
		Entry<Account, ParticipationStatus>, Persistable<String>
{
	private static final long serialVersionUID = -5325769114002875339L;

	@Id
	@Column(updatable = false)
	private String id = UUID.randomUUID().toString();

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ENTRY_ID", nullable = false, updatable = false)
	private JPACalendarEvent entry;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID", nullable = false, updatable = false)
	private Account account;

	@Enumerated
	@NotNull
	@Column(nullable = false)
	private ParticipationStatus status;

	@SuppressWarnings("unused")
	private CalendarEntryParticipant()
	{
	}

	public CalendarEntryParticipant(JPACalendarEvent entry, Account account,
			ParticipationStatus status)
	{
		Assert.noNullElements(new Object[] { entry, account, status },
				"I pity the fool...");
		this.entry = entry;
		this.account = account;
		this.status = status;
	}

	public CalendarEntryParticipant(JPACalendarEvent entry, Account account)
	{
		this(entry, account, ParticipationStatus.PENDING);
	}

	@Override
	public Account getKey()
	{
		return account;
	}

	@Override
	public ParticipationStatus getValue()
	{
		return status;
	}

	@Override
	public ParticipationStatus setValue(ParticipationStatus value)
	{
		return status = value;
	}

	@Override
	public boolean isNew()
	{
		return entry == null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		CalendarEntryParticipant other = (CalendarEntryParticipant) obj;
		if (account == null)
		{
			if (other.account != null)
			{
				return false;
			}
		}
		else if (!account.equals(other.account))
		{
			return false;
		}
		if (entry == null)
		{
			if (other.entry != null)
			{
				return false;
			}
		}
		else if (!entry.equals(other.entry))
		{
			return false;
		}
		return true;
	}

	public static List<CalendarEntryParticipant> findByAccounts(
			Account... account)
	{
		QCalendarEntryParticipant p = QCalendarEntryParticipant.calendarEntryParticipant;
		return QueryFactory.from(p).where(p.account.in(account)).list(p);
	}
}
