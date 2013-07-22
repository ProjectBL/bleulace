package com.bleulace.mgt.presentation;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

@Component
class ScheduleStatusFinderImpl implements ScheduleStatusFinder
{
	@Override
	public ScheduleStatus find(String accountId, Date start, Date end)
	{
		for (ScheduleStatus status : ScheduleStatus.values())
		{
			if (status.is(accountId, Range.between(start, end)))
			{
				return status;
			}
		}
		throw new IllegalStateException();
	}
}