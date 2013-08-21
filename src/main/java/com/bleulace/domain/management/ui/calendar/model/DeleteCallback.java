package com.bleulace.domain.management.ui.calendar.model;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.model.EventModel.EventModelCallback;

class DeleteCallback implements EventModelCallback
{
	private final CalendarViewContext ctx;

	public DeleteCallback(CalendarViewContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public void execute(EventModel model)
	{
		ctx.fireEventChange();
	}
}