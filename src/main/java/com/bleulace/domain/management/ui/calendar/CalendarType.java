package com.bleulace.domain.management.ui.calendar;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

enum CalendarType implements Converter<Date, Range<Date>>
{
	DAY(new DayConverter()), WEEK(new WeekConverter()), MONTH(
			new MonthConverter());

	private final String displayName;

	private final Converter<Date, Range<Date>> converter;

	private CalendarType(Converter<Date, Range<Date>> converter)
	{
		this.converter = converter;
		this.displayName = StringUtils.capitalize(this.name().toLowerCase());
	}

	@Override
	public Range<Date> convert(Date source)
	{
		Assert.notNull(source);
		return converter.convert(source);
	}

	@Override
	public String toString()
	{
		return displayName;
	}

	private static class DayConverter implements Converter<Date, Range<Date>>
	{
		@Override
		public Range<Date> convert(Date source)
		{
			LocalDateTime start = toMidnight(source);
			return Range.between(start.toDate(),
					start.plusDays(1).minusMillis(1).toDate());
		}
	}

	private static class WeekConverter implements Converter<Date, Range<Date>>
	{
		@Override
		public Range<Date> convert(Date source)
		{
			LocalDateTime start = toMidnight(source).withDayOfWeek(1);
			return Range.between(start.toDate(), start.plusWeeks(1)
					.minusMillis(1).toDate());
		}
	}

	private static class MonthConverter implements Converter<Date, Range<Date>>
	{
		@Override
		public Range<Date> convert(Date source)
		{
			LocalDateTime start = toMidnight(source).withDayOfWeek(1);
			return Range.between(start.toDate(), start.plusMonths(1)
					.minusMillis(1).toDate());
		}
	}

	private static LocalDateTime toMidnight(Date source)
	{
		return LocalDateTime.fromDateFields(source).withHourOfDay(0)
				.withMinuteOfHour(0).withSecondOfMinute(0)
				.withMillisOfSecond(0);
	}
}