package com.bleulace.mgt.presentation;

import java.util.Date;

import org.apache.commons.lang3.Range;

import com.bleulace.mgt.domain.EventDAO;
import com.bleulace.mgt.domain.EventInvitationDAO;
import com.bleulace.utils.ctx.SpringApplicationContext;

public enum ScheduleStatus implements ScheduleStatusFinder
{
	AVAILABLE(new AvailableScheduleFinder()), TENTATIVE(
			new TentativeScheduleFinder()), BUSY(new BusyScheduleFinder());

	private final ScheduleStatusFinder finder;

	private ScheduleStatus(ScheduleStatusFinder finder)
	{
		this.finder = finder;
	}

	@Override
	public boolean is(String accountId, Range<Date> range)
	{
		return finder.is(accountId, range);
	}

	public static ScheduleStatus get(String accountId, Date start, Date end)
	{
		for (ScheduleStatus status : ScheduleStatus.values())
		{
			if (status.is(accountId, Range.between(start, end)))
			{
				return status;
			}
		}
		throw new IllegalStateException();
	}

	private static class AvailableScheduleFinder implements
			ScheduleStatusFinder
	{
		@Override
		public boolean is(String accountId, Range<Date> range)
		{
			return !(BUSY.is(accountId, range) || TENTATIVE
					.is(accountId, range));
		}
	}

	private static class BusyScheduleFinder implements ScheduleStatusFinder
	{
		@Override
		public boolean is(String accountId, Range<Date> range)
		{
			return SpringApplicationContext.getBean(EventDAO.class)
					.countByAttendeeAndDates(accountId, range.getMinimum(),
							range.getMaximum()) > 0;
		}
	}

	private static class TentativeScheduleFinder implements
			ScheduleStatusFinder
	{
		@Override
		public boolean is(String accountId, Range<Date> range)
		{
			return SpringApplicationContext.getBean(EventInvitationDAO.class)
					.countByAttendeeAndDates(accountId, range.getMinimum(),
							range.getMaximum()) > 0;
		}
	}
}