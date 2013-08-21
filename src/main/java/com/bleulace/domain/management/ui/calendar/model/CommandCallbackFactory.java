package com.bleulace.domain.management.ui.calendar.model;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.model.EventModel.EventModelCallback;

public interface CommandCallbackFactory
{
	public EventModelCallback makeCreateCallback(CalendarViewContext ctx);

	public EventModelCallback makeEditCallback(CalendarViewContext ctx);

	public EventModelCallback makeDeleteCallback(CalendarViewContext ctx);
}