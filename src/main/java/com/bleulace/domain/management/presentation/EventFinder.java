package com.bleulace.domain.management.presentation;

import java.util.Date;
import java.util.List;

public interface EventFinder
{
	public List<EventDTO> findByAccountForDates(String accountId, Date start,
			Date end);
}