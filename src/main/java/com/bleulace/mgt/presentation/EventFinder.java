package com.bleulace.mgt.presentation;

import java.util.List;

import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.dto.Finder;

public interface EventFinder extends Finder<EventDTO>
{
	public List<EventDTO> findByAssignment(String accountId,
			ManagementAssignment assignment);
}