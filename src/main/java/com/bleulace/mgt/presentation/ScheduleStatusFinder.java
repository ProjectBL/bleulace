package com.bleulace.mgt.presentation;

import java.util.Date;

import org.apache.commons.lang3.Range;

public interface ScheduleStatusFinder
{
	boolean is(String accountId, Range<Date> range);
}