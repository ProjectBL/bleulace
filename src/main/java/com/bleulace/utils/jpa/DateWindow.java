package com.bleulace.utils.jpa;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.Range;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.util.Assert;

@Embeddable
@RooEquals
public class DateWindow implements Serializable
{
	private static final long serialVersionUID = -1900206502831675688L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date start;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date end;

	@SuppressWarnings("unused")
	private DateWindow()
	{
	}

	public DateWindow(LocalDateTime startTime, Period length)
	{
		init(startTime, length);
	}

	public DateWindow(Date from, Date to)
	{
		Range<Date> range = Range.between(from, to);
		LocalDateTime startTime = LocalDateTime.fromDateFields(range
				.getMinimum());
		Period length = Period.fieldDifference(startTime,
				LocalDateTime.fromDateFields(range.getMaximum()));
		init(startTime, length);
	}

	public static DateWindow defaultValue()
	{
		return new DateWindow(LocalDateTime.now().plusMinutes(15),
				Period.hours(1));
	}

	public Date getStart()
	{
		return start;
	}

	public Date getEnd()
	{
		return end;
	}

	public LocalDateTime getStartTime()
	{
		return LocalDateTime.fromDateFields(start);
	}

	public Period getLength()
	{
		return Period.fieldDifference(LocalDateTime.fromDateFields(start),
				LocalDateTime.fromDateFields(end));
	}

	public DateWindow withEnd(Date end)
	{
		return new DateWindow(getStart(), end);
	}

	public DateWindow withStart(Date start)
	{
		return new DateWindow(start, getEnd());
	}

	public DateWindow move(Date newStart)
	{
		return new DateWindow(LocalDateTime.fromDateFields(newStart),
				getLength());
	}

	public Range<Date> getRange()
	{
		return Range.between(getStart(), getEnd());
	}

	public boolean isAllDay()
	{
		return getLength().getDays() > 0;
	}

	private void init(LocalDateTime startTime, Period length)
	{
		Assert.noNullElements(new Object[] { startTime, length });
		this.start = format(startTime.toDate());
		this.end = format(startTime.plus(length).toDate());
	}

	private Date format(Date date)
	{
		return LocalDateTime.fromDateFields(date).withSecondOfMinute(0)
				.withMillisOfSecond(0).toDate();
	}

	static class LocalDateTimeComparator implements Comparator<LocalDateTime>
	{
		@Override
		public int compare(LocalDateTime o1, LocalDateTime o2)
		{
			return (int) (o1.toDateTime().getMillis() - o2.toDateTime()
					.getMillis());
		}
	}
}