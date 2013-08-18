package com.bleulace.domain.management.infrastructure;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bleulace.domain.management.model.Event;
import com.bleulace.domain.management.model.RsvpStatus;

interface EventDAOCustom
{
	public List<Event> findEvents(Date start, Date end, String accountId);

	public List<Event> findEvents(Date instant, String accountId);

	public Set<RsvpStatus> findRsvps(Date start, Date end, String accountId);
}