package com.bleulace.domain.management.presentation;

import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.LocalTime;

public interface ScheduleStatusFinder
{
	Map<LocalTime, ScheduleStatus> findScheduleStatus(String accountId,
			Date start, Date end, TimeZone tz);
}
