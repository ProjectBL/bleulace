package com.bleulace.web.demo.calendar.span;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.vaadin.ui.Calendar;

class WeekSpan implements CalendarSpanCallback
{
	@Override
	public void resize(Calendar calendar)
	{
		DateTime start = LocalDate.fromDateFields(calendar.getStartDate())
				.toDateTimeAtStartOfDay().withDayOfWeek(1);
		calendar.setStartDate(start.toDate());
		calendar.setEndDate(start.plusWeeks(1).minusMillis(1).toDate());
	}
}