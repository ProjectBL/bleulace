package com.bleulace.domain.management.ui.calendar.model;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.model.EventModel.EventModelCallback;

class CreateCallback implements EventModelCallback, CommandGatewayAware
{
	private final CalendarViewContext ctx;

	CreateCallback(CalendarViewContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public void execute(EventModel model)
	{
		sendAndWait(new CreateEventCommand(model.getCaption(),
				model.getDescription(), model.getStart(), model.getEnd()));
		ctx.fireEventChange();
	}
}