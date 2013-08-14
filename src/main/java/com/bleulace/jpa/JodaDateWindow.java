package com.bleulace.jpa;

import java.util.Date;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

public class JodaDateWindow
{
	private final LocalDateTime startTime;
	private final Period length;

	public JodaDateWindow(Date start, Date end)
	{
		startTime = LocalDateTime.fromDateFields(start);
		length = Period.fieldDifference(startTime,
				LocalDateTime.fromDateFields(end));
	}

	public Period getLength()
	{
		return Period.fieldDifference(getStart(), getEnd());
	}

	public LocalDateTime getStart()
	{
		return startTime;
	}

	public LocalDateTime getEnd()
	{
		return startTime.plus(length);
	}

	public JodaDateWindow(LocalDateTime startTime, Period length)
	{
		this.startTime = startTime;
		this.length = length;
	}

	public static JodaDateWindow defaultValue()
	{
		return new JodaDateWindow(LocalDateTime.now().plusMinutes(15),
				Period.hours(1));
	}

	public JodaDateWindow move(LocalDateTime newStart)
	{
		return new JodaDateWindow(newStart, getLength());
	}

	public boolean isAllDay()
	{
		return getLength().getDays() > 0;
	}

	public DateWindow getDateWindow()
	{
		return new DateWindow(getStart().toDate(), getEnd().toDate());
	}
}