package com.bleulace.mgt.domain.event;

import com.bleulace.utils.jpa.LocalDateTimeRange;

public class EventRescheduledEvent
{
	private static final long serialVersionUID = 818429591163191885L;

	private LocalDateTimeRange range;

	public EventRescheduledEvent()
	{
	}

	public EventRescheduledEvent(LocalDateTimeRange range)
	{
		this.range = range;
	}

	public LocalDateTimeRange getRange()
	{
		return range;
	}
}