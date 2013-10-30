package com.bleulace.domain.management.infrastructure;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;

interface EventDAOCustom
{
	public List<PersistentEvent> findEvents(Date start, Date end,
			String accountId);

	public List<PersistentEvent> findEvents(Date start, Date end,
			Collection<String> accountIds);

	public List<PersistentEvent> findEvents(Date start, Date end,
			Collection<String> accountIds, Collection<String> cachedEventIds);

	public List<PersistentEvent> findEvents(Date instant, String accountId);

	public List<PersistentEvent> findEvents(String accountId);

	public boolean exists(Date start, Date end, Collection<String> accountIds);

	public Set<RsvpStatus> findRsvps(Date start, Date end, String accountId);
}