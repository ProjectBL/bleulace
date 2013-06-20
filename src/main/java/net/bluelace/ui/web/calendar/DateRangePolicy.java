package net.bluelace.ui.web.calendar;

import java.util.Date;

import net.bluelace.ui.web.calendar.CalendarType.RequestDirection;

import org.apache.commons.lang3.Range;

public interface DateRangePolicy
{
	public String getTitle(Date cursor);

	public Range<Date> getDateRange(Date cursor);

	public Date moveCursor(Date cursor, RequestDirection direction);
}