package com.bleulace.web.demo.calendar.span;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.vaadin.ui.Calendar;

class DaySpan implements CalendarSpanCallback
{
	@Override
	public void resize(Calendar calendar)
	{
		DateTime start = LocalDate.fromDateFields(calendar.getStartDate())
				.toDateTimeAtStartOfDay();
		calendar.setStartDate(start.toDate());
		calendar.setEndDate(start.plusDays(1).minusMillis(1).toDate());
	}
}