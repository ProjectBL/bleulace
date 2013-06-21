package com.bleulace.ui.web.calendar;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public enum CalendarType implements DateRangePolicy
{
	DAY("Day", new TodayDateRangePolicy()), WEEK("Week",
			new WeekDateRangePolicy()), MONTH("Month",
			new MonthDateRangePolicy());

	public enum RequestDirection
	{
		FORWARD, BACKWARD;
	}

	private final String name;
	private final DateRangePolicy policy;

	private CalendarType(String name, DateRangePolicy policy)
	{
		this.policy = policy;
		this.name = name;
	}

	@Override
	public Range<Date> getDateRange(Date cursor)
	{
		return policy.getDateRange(cursor);
	}

	@Override
	public Date moveCursor(Date cursor, RequestDirection direction)
	{
		return policy.moveCursor(cursor, direction);
	}

	@Override
	public String getTitle(Date cursor)
	{
		return policy.getTitle(cursor);
	}

	@Override
	public String toString()
	{
		return name;
	}

	static class TodayDateRangePolicy implements DateRangePolicy
	{
		@Override
		public Range<Date> getDateRange(Date cursor)
		{
			return Range.between(cursor, cursor);
		}

		@Override
		public Date moveCursor(Date cursor, RequestDirection direction)
		{
			switch (direction)
			{
			case BACKWARD:
				return LocalDate.fromDateFields(cursor).minusDays(1).toDate();
			case FORWARD:
				return LocalDate.fromDateFields(cursor).plusDays(1).toDate();
			default:
				return null;
			}
		}

		@Override
		public String getTitle(Date cursor)
		{
			return null;
		}
	}

	static class WeekDateRangePolicy implements DateRangePolicy
	{
		@Override
		public Range<Date> getDateRange(Date cursor)
		{
			return Range.between(startOfWeek(cursor), endOfWeek(cursor));
		}

		@Override
		public Date moveCursor(Date cursor, RequestDirection direction)
		{
			switch (direction)
			{
			case BACKWARD:
				return LocalDate.fromDateFields(cursor).minusWeeks(1).toDate();
			case FORWARD:
				return LocalDate.fromDateFields(cursor).plusWeeks(1).toDate();
			default:
				return null;
			}
		}

		private Date startOfWeek(Date cursor)
		{
			LocalDate value = LocalDate.fromDateFields(cursor);
			while (value.getDayOfWeek() != DateTimeConstants.SUNDAY)
			{
				value = value.minusDays(1);
			}
			return value.toDateMidnight().toDate();
		}

		private Date endOfWeek(Date cursor)
		{
			LocalDate value = LocalDate.fromDateFields(cursor);
			while (value.getDayOfWeek() != DateTimeConstants.SATURDAY)
			{
				value = value.plusDays(1);
			}
			return value.toDateMidnight().toDate();
		}

		private String boundaryAsString(Date boundary)
		{
			LocalDate localDate = LocalDate.fromDateFields(boundary);
			return localDate.monthOfYear().getAsText() + " "
					+ localDate.dayOfMonth().getAsText();
		}

		@Override
		public String getTitle(Date cursor)
		{
			Date start = startOfWeek(cursor);
			Date end = endOfWeek(cursor);
			return boundaryAsString(start) + " to " + boundaryAsString(end);
		}
	}

	static class MonthDateRangePolicy implements DateRangePolicy
	{
		@Override
		public Range<Date> getDateRange(Date cursor)
		{
			return Range.between(startOfMonth(cursor), endOfMonth(cursor));
		}

		@Override
		public Date moveCursor(Date cursor, RequestDirection direction)
		{
			switch (direction)
			{
			case BACKWARD:
				return LocalDate.fromDateFields(cursor).minusMonths(1).toDate();
			case FORWARD:
				return LocalDate.fromDateFields(cursor).plusMonths(1).toDate();
			default:
				return null;
			}
		}

		private Date startOfMonth(Date cursor)
		{
			LocalDate value = LocalDate.fromDateFields(cursor);
			return new LocalDate(value.getYear(), value.getMonthOfYear(), 1)
					.toDate();
		}

		private Date endOfMonth(Date cursor)
		{
			LocalDate value = LocalDate.fromDateFields(cursor);
			int year = value.getYear();
			int monthOfYear = value.getMonthOfYear() + 1;
			if (monthOfYear == 0)
			{
				monthOfYear = 12;
				year--;
			}
			if (monthOfYear == 13)
			{
				monthOfYear = 1;
				year++;
			}
			return new LocalDate(year, monthOfYear, 1).minusDays(1).toDate();
		}

		@Override
		public String getTitle(Date cursor)
		{
			LocalDate date = LocalDate.fromDateFields(cursor);
			return date.monthOfYear().getAsText() + " "
					+ date.year().getAsText();
		}
	}
}