package com.bleulace.domain.calendar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.account.Account;
import com.frugalu.api.messaging.jpa.EntityManagerReference;
import com.mysema.query.jpa.impl.JPAQuery;

class ParticipantMap implements Map<Account, ParticipationStatus>
{
	private final CalendarEntry entry;

	private static final QCalendarEntryParticipant p = QCalendarEntryParticipant.calendarEntryParticipant;

	public ParticipantMap(CalendarEntry entry)
	{
		Assert.notNull(entry);
		this.entry = entry;
	}

	@Override
	public int size()
	{
		return entry.getEntryParticipants().size();
	}

	@Override
	public boolean isEmpty()
	{
		return entry.getEntryParticipants().isEmpty();
	}

	@Override
	public boolean containsKey(Object key)
	{
		if (key.getClass().isAssignableFrom(Account.class))
		{
			return makeQuery().where(p.account.eq((Account) key)).exists();
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value)
	{
		if (value.getClass().isAssignableFrom(ParticipationStatus.class))
		{
			return makeQuery().where(p.status.eq((ParticipationStatus) value))
					.exists();
		}
		return false;
	}

	@Override
	public ParticipationStatus get(Object key)
	{
		if (key.getClass().isAssignableFrom(Account.class))
		{
			CalendarEntryParticipant participant = findOne((Account) key);
			return participant == null ? null : participant.getStatus();
		}
		return null;
	}

	@Override
	public ParticipationStatus put(Account key, ParticipationStatus value)
	{
		if (get(key) == null)
		{
			entry.getEntryParticipants().add(
					new CalendarEntryParticipant(entry, key, value));
		}
		else if (get(key) != value)
		{
			findOne(key).setStatus(value);
		}
		return value;
	}

	@Override
	@Transactional
	public ParticipationStatus remove(Object key)
	{
		if (key.getClass().isAssignableFrom(Account.class))
		{
			CalendarEntryParticipant participant = makeQuery().where(
					p.account.eq((Account) key)).singleResult(p);
			if (participant != null)
			{
				entry.getParticipants().remove(participant);
				EntityManagerReference.get().remove(participant);
				return participant.getStatus();
			}
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends Account, ? extends ParticipationStatus> m)
	{
		for (Account key : m.keySet())
		{
			if (key != null)
			{
				ParticipationStatus value = get(m);
				if (value != null)
				{
					put(key, value);
				}
			}
		}
	}

	@Override
	public void clear()
	{
		entry.getParticipants().clear();
	}

	@Override
	public Set<Account> keySet()
	{
		return new HashSet<Account>(makeQuery().list(p.account));
	}

	@Override
	public Collection<ParticipationStatus> values()
	{
		return makeQuery().list(p.status);
	}

	@Override
	public Set<java.util.Map.Entry<Account, ParticipationStatus>> entrySet()
	{
		return new HashSet<Entry<Account, ParticipationStatus>>(makeQuery()
				.list(p));
	}

	private JPAQuery makeQuery()
	{
		return QueryFactory.from(p).where(p.entry.eq(entry));
	}

	private CalendarEntryParticipant findOne(Account account)
	{
		return makeQuery().where(p.account.eq(account)).singleResult(p);
	}
}
