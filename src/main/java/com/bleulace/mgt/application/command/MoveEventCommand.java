package com.bleulace.mgt.application.command;

import java.util.Date;

import javax.validation.constraints.Future;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class MoveEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@Future
	private Date newStart;

	public MoveEventCommand(String id)
	{
		this(id, null);
	}

	public MoveEventCommand(String id, Date newStart)
	{
		Assert.notNull(id);
		this.id = id;
		this.newStart = newStart;
	}

	public Date getNewStart()
	{
		return newStart;
	}
}
