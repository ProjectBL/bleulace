package com.bleulace.domain.management.ui.calendar.model;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.model.EventModel.EventModelCallback;
import com.bleulace.utils.dto.Factory;

@Factory(makes = { EventModelCallback.class })
class CommandCallbackFactoryImpl implements CommandCallbackFactory
{
	@Override
	public EventModelCallback makeCreateCallback(CalendarViewContext ctx)
	{
		return new CreateCallback(ctx);
	}

	@Override
	public EventModelCallback makeEditCallback(CalendarViewContext ctx)
	{
		return new EditCallback(ctx);
	}

	@Override
	public EventModelCallback makeDeleteCallback(CalendarViewContext ctx)
	{
		return new DeleteCallback(ctx);
	}

}
