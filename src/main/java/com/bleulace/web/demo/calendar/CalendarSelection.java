package com.bleulace.web.demo.calendar;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

enum CalendarSelection
{
	//@formatter:off
		DAY("Day",new DayCommand()), 
		WEEK("Week",new WeekCommand()), 
		MONTH("Month",new MonthCommand());
		//@formatter:on

	final String selectionName;
	final CalendarCommand command;

	CalendarSelection(String selectionName, CalendarCommand command)
	{
		this.selectionName = selectionName;
		this.command = command;
	}

	@Override
	public String toString()
	{
		return selectionName;
	}

	interface CalendarCommand
	{
		Range<Date> execute(Date cursor);
	}

	static class DayCommand implements CalendarCommand
	{
		@Override
		public Range<Date> execute(Date cursor)
		{
			DateTime start = LocalDate.fromDateFields(cursor)
					.toDateTimeAtStartOfDay();
			return Range.between(start.toDate(),
					start.plusDays(1).minusMillis(1).toDate());
		}
	}

	static class WeekCommand implements CalendarCommand
	{
		@Override
		public Range<Date> execute(Date cursor)
		{
			DateTime start = LocalDate.fromDateFields(cursor)
					.toDateTimeAtStartOfDay();
			start = start.withDayOfWeek(start.toGregorianCalendar()
					.getFirstDayOfWeek());
			return Range.between(start.toDate(), start.plusWeeks(1)
					.minusMillis(1).toDate());
		}
	}

	static class MonthCommand implements CalendarCommand
	{
		@Override
		public Range<Date> execute(Date cursor)
		{
			DateTime start = LocalDate.fromDateFields(cursor)
					.toDateTimeAtStartOfDay();
			start = start.withDayOfWeek(start.toGregorianCalendar()
					.getFirstDayOfWeek());
			start = start.withDayOfMonth(1);
			return Range.between(start.toDate(), start.plusMonths(1)
					.minusMillis(1).toDate());
		}
	}
}
