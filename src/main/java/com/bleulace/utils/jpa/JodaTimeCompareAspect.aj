package com.bleulace.utils.jpa;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

public aspect JodaTimeCompareAspect
{
	declare parents : Period implements Comparable<Period>;

	public int Period.compareTo(Period that)
	{
		return this.getMillis() - that.getMillis();
	}
}