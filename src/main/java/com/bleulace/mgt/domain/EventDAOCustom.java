package com.bleulace.mgt.domain;

import java.util.Date;
import java.util.List;

interface EventDAOCustom
{
	public List<Event> findByAssignment(String accountId,
			ManagementAssignment assignment);

	public List<Event> findByAssignment(String accountId);

	public List<Event> findBetweenDates(Date start, Date end);
}
