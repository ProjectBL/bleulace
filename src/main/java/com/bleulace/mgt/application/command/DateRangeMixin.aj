package com.bleulace.mgt.application.command;

import java.util.Date;

import com.bleulace.mgt.domain.DateRange;
import com.bleulace.mgt.domain.FutureRange;

public interface DateRangeMixin
{
	static aspect Impl
	{
		@FutureRange
		private DateRange DateRangeMixin.range = new DateRange();

		public Date DateRangeMixin.getStart()
		{
			return range.getStart();
		}

		public Date DateRangeMixin.getEnd()
		{
			return range.getEnd();
		}

		public void DateRangeMixin.setStart(Date start)
		{
			range.setStart(start);
		}

		public void DateRangeMixin.setEnd(Date end)
		{
			range.setEnd(end);
		}
	}
}