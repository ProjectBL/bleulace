package com.bleulace.domain.management.ui.calendar.context;

import org.springframework.util.Assert;

import com.bleulace.utils.dto.Factory;

@Factory(makes = CalendarViewContext.class)
class CalendarViewContextFactoryImpl implements CalendarViewContextFactory
{
	@Override
	public CalendarViewContext make(String ownerId, String viewerId)
	{
		Assert.notNull(ownerId);
		Assert.notNull(viewerId);
		if (ownerId.equals(viewerId))
		{
			return new SelfCalendarViewContext(ownerId);
		}
		return new OtherCalendarViewContext(ownerId, viewerId);
	}
}
