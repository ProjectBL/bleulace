package com.bleulace.mgt.presentation;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.joda.time.LocalDateTime;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.utils.chrono.LocalDateTimeComparator;

@RooJavaBean(settersByDefault = false)
public class ScheduleStatusDTO
{
	private Range<LocalDateTime> range;

	private ScheduleStatus status;

	public ScheduleStatusDTO(Date from, Date to, ScheduleStatus status)
	{
		Assert.noNullElements(new Object[] { from, to, status });
		this.range = Range.between(LocalDateTime.fromDateFields(from),
				LocalDateTime.fromDateFields(to),
				LocalDateTimeComparator.INSTANCE);
		this.status = status;
	}
}