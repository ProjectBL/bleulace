package com.bleulace.domain.management.presentation;

import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
class ScheduleStatusFinderImpl implements ScheduleStatusFinder
{
	@Override
	public Map<LocalTime, ScheduleStatus> findScheduleStatus(String accountId,
			Date start, Date end, TimeZone tz)
	{
		return new ScheduleStatusBuilder(accountId, start, end, tz).build();
	}
}
