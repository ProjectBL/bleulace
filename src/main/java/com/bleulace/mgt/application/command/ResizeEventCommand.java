package com.bleulace.mgt.application.command;

import java.util.Date;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.util.Assert;

public class ResizeEventCommand implements DateRangeMixin
{
	@TargetAggregateIdentifier
	private final String id;

	public ResizeEventCommand(String id)
	{
		this(id, null, null);
	}

	public ResizeEventCommand(String id, Date start, Date end)
	{
		Assert.notNull(id);
		this.id = id;
		setStart(start);
		setEnd(end);
	}

	public String getId()
	{
		return id;
	}
}