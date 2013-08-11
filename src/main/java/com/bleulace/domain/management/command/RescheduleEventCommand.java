package com.bleulace.domain.management.command;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class RescheduleEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@Future
	@NotNull
	private final Date start;

	private final Date end;

	public RescheduleEventCommand(String id, Date newStart)
	{
		this(id, newStart, null);
	}

	public RescheduleEventCommand(String id, Date newStart, Date newEnd)
	{
		this.id = id;
		this.start = newStart;
		this.end = newEnd;
	}
}