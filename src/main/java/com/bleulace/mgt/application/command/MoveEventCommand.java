package com.bleulace.mgt.application.command;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class MoveEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotNull
	@Future
	private Date start;

	public MoveEventCommand(String id)
	{
		Assert.notNull(id);
		this.id = id;
	}

	public MoveEventCommand(String id, Date start)
	{
		this(id);
		this.start = start;
	}
}