package com.bleulace.mgt.presentation;

import java.util.Date;

import org.apache.commons.lang3.Range;

import com.bleulace.utils.spec.Specification;

public class ScheduleSpecification implements Specification<Range<Date>>
{
	private final String accountId;
	private final ScheduleStatus status;

	public ScheduleSpecification(String accountId, ScheduleStatus status)
	{
		this.accountId = accountId;
		this.status = status;
	}

	@Override
	public boolean isSatisfiedBy(Range<Date> candidate)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
