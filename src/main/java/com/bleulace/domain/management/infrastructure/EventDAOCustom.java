package com.bleulace.domain.management.infrastructure;

import java.util.Date;
import java.util.List;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;

interface EventDAOCustom
{
	public List<PersistentEvent> findEvents(Date start, Date end,
			String accountId);

	public RsvpStatus findStatus(String eventId, String accountId);

	public List<Account> findParticipants(PersistentEvent event);
}