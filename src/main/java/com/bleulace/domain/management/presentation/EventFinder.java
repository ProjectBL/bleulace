package com.bleulace.domain.management.presentation;

import java.util.Date;
import java.util.List;

import com.bleulace.utils.dto.Finder;

public interface EventFinder extends Finder<EventDTO>
{
	public List<EventDTO> findByAccountIdForDates(String accountId, Date start,
			Date end);
}