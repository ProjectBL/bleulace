package com.bleulace.utils.chrono;

import java.util.Comparator;

import org.joda.time.LocalDateTime;

public class LocalDateTimeComparator implements Comparator<LocalDateTime>
{
	public static final LocalDateTimeComparator INSTANCE = new LocalDateTimeComparator();

	private LocalDateTimeComparator()
	{
	}

	@Override
	public int compare(LocalDateTime o1, LocalDateTime o2)
	{
		return (int) (o1.toDateTime().getMillis() - o2.toDateTime().getMillis());
	}

}