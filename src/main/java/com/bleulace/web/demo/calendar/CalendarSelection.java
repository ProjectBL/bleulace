package com.bleulace.web.demo.calendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Weeks;
import org.springframework.util.Assert;

import com.vaadin.ui.Calendar;

enum CalendarSelection implements CalendarCommand
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

	@Override
	public void execute(Calendar calendar)
	{
		Assert.notNull(calendar);
		command.execute(calendar);
	}

	@Override
	public boolean matches(Calendar calendar)
	{
		Assert.notNull(calendar);
		return command.matches(calendar);
	}

	private static class DayCommand implements CalendarCommand
	{
		@Override
		public void execute(Calendar calendar)
		{
			DateTime start = LocalDate.fromDateFields(calendar.getStartDate())
					.toDateTimeAtStartOfDay();
			calendar.setStartDate(start.toDate());
			calendar.setEndDate(start.plusDays(1).minusMillis(1).toDate());
		}

		@Override
		public boolean matches(Calendar calendar)
		{
			LocalDateTime start = LocalDateTime.fromDateFields(calendar
					.getStartDate());
			LocalDateTime end = LocalDateTime.fromDateFields(calendar
					.getEndDate());
			return Days.daysBetween(start, end).isLessThan(Days.ONE);
		}
	}

	private static class WeekCommand implements CalendarCommand
	{
		@Override
		public void execute(Calendar calendar)
		{
			DateTime start = LocalDate.fromDateFields(calendar.getStartDate())
					.toDateTimeAtStartOfDay();
			start = start.withDayOfWeek(start.toGregorianCalendar()
					.getFirstDayOfWeek());
			calendar.setStartDate(start.toDate());
			calendar.setEndDate(start.plusWeeks(1).minusMillis(1).toDate());
		}

		@Override
		public boolean matches(Calendar calendar)
		{
			LocalDateTime start = LocalDateTime.fromDateFields(calendar
					.getStartDate());
			LocalDateTime end = LocalDateTime.fromDateFields(calendar
					.getEndDate());
			return !DAY.matches(calendar)
					&& Weeks.weeksBetween(start, end).isLessThan(Weeks.ZERO);
		}
	}

	private static class MonthCommand implements CalendarCommand
	{
		@Override
		public void execute(Calendar calendar)
		{
			DateTime start = LocalDate.fromDateFields(calendar.getStartDate())
					.toDateTimeAtStartOfDay();
			start = start.withDayOfWeek(start.toGregorianCalendar()
					.getFirstDayOfWeek());
			start = start.withDayOfMonth(1);
			calendar.setStartDate(start.toDate());
			calendar.setEndDate(start.plusMonths(1).minusMillis(1).toDate());
		}

		@Override
		public boolean matches(Calendar calendar)
		{
			return !DAY.matches(calendar) && !WEEK.matches(calendar);
		}
	}
}
