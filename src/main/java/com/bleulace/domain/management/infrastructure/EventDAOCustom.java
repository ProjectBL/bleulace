package com.bleulace.domain.management.infrastructure;

import java.util.Date;
import java.util.List;

import com.bleulace.domain.management.model.Event;

interface EventDAOCustom
{
	public List<Event> findEvents(Date start, Date end, String accountId);
}