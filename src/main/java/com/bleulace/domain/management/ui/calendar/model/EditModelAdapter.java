package com.bleulace.domain.management.ui.calendar.model;

import javax.validation.Valid;

import com.bleulace.domain.management.command.EditEventCommand;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;

class EditModelAdapter extends EventModelImpl implements
		EventModelAdapter<EditEventCommand>
{
	public EditModelAdapter(String id, CalendarViewContext ctx)
	{
		super(id, ctx);
	}

	@Valid
	@Override
	public EditEventCommand getAdaptedCommand()
	{
		return new EditEventCommand(getId(), getCaption(), getDescription(),
				getStart(), getEnd());
	}

}
