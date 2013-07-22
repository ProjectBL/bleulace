package com.bleulace.mgt.presentation;

import java.util.Date;

import org.apache.commons.lang3.Range;

import com.bleulace.mgt.domain.EventDAO;
import com.bleulace.mgt.domain.EventInvitationDAO;
import com.bleulace.mgt.presentation.ScheduleStatusFinder.ScheduleStatusInquirer;
import com.bleulace.utils.ctx.SpringApplicationContext;

public enum ScheduleStatus implements ScheduleStatusInquirer
{
	AVAILABLE(new AvailableScheduleFinder()), TENTATIVE(
			new TentativeScheduleFinder()), BUSY(new BusyScheduleFinder());

	private final ScheduleStatusInquirer finder;

	private ScheduleStatus(ScheduleStatusInquirer finder)
	{
		this.finder = finder;
	}

	@Override
	public boolean is(String accountId, Range<Date> range)
	{
		return finder.is(accountId, range);
	}

	private static class AvailableScheduleFinder implements
			ScheduleStatusInquirer
	{
		@Override
		public boolean is(String accountId, Range<Date> range)
		{
			return !(BUSY.is(accountId, range) || TENTATIVE
					.is(accountId, range));
		}
	}

	private static class BusyScheduleFinder implements ScheduleStatusInquirer
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
			ScheduleStatusInquirer
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