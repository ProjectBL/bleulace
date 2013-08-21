package com.bleulace.domain.management.ui.calendar.model;

import javax.validation.Valid;

import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;

class CreateModelAdapter extends EventModelImpl implements
		EventModelAdapter<CreateEventCommand>
{
	public CreateModelAdapter(CalendarViewContext ctx)
	{
		super(null, ctx);
	}

	@Valid
	@Override
	public CreateEventCommand getAdaptedCommand()
	{
		return new CreateEventCommand(getCaption(), getDescription(),
				getStart(), getEnd());
	}

}
