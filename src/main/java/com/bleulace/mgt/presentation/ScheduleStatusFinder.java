package com.bleulace.mgt.presentation;

import java.util.Date;

import org.apache.commons.lang3.Range;

public interface ScheduleStatusFinder
{
	public ScheduleStatus find(String accountId, Date start, Date end);

	interface ScheduleStatusInquirer
	{
		boolean is(String accountId, Range<Date> range);
	}
}
