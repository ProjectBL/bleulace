package com.bleulace.mgt.presentation;

import java.util.Date;
import java.util.List;

import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.dto.Finder;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
public interface EventFinder extends Finder<EventDTO>
{
	public List<EventDTO> findByAccountIdAndRange(String accountId, Date start,
			Date end);

	public List<EventDTO> findByAssignment(String accountId,
			ManagementAssignment assignment);
}