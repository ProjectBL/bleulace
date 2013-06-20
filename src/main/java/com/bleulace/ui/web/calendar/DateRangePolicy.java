package com.bleulace.ui.web.calendar;

import java.util.Date;

import org.apache.commons.lang3.Range;

import com.bleulace.ui.web.calendar.CalendarType.RequestDirection;

public interface DateRangePolicy
{
	public String getTitle(Date cursor);

	public Range<Date> getDateRange(Date cursor);

	public Date moveCursor(Date cursor, RequestDirection direction);
}