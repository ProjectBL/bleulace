package com.bleulace.domain.management.ui.calendar.model;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.command.EditEventCommand;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.model.EventModel.EventModelCallback;

class EditCallback implements EventModelCallback, CommandGatewayAware
{
	private final CalendarViewContext ctx;

	public EditCallback(CalendarViewContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public void execute(EventModel model)
	{
		EditEventCommand command = new EditEventCommand(model.getId(),
				model.getCaption(), model.getDescription(), model.getStart(),
				model.getEnd());
		sendAndWait(command);
		ctx.fireEventChange();
	}
}