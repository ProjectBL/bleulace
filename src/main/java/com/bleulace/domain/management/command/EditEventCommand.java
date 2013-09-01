package com.bleulace.domain.management.command;

import java.util.Date;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class EditEventCommand extends CreateEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	public EditEventCommand(String id)
	{
		this.id = id;
	}

	public EditEventCommand(String id, String title, String location,
			Date start, Date end)
	{
		super(title, location, start, end);
		this.id = id;
	}

	@Override
	public String getId()
	{
		return id;
	}
}
