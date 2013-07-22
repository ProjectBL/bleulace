package com.bleulace.mgt.application.command;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.utils.jpa.DateWindow;

@RooJavaBean
public class ResizeEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@NotNull
	private DateWindow window;

	public ResizeEventCommand(String id)
	{
		Assert.notNull(id);
		this.id = id;
		window = new DateWindow(LocalDateTime.now().plusMinutes(15),
				Period.hours(1));
	}

	public ResizeEventCommand(String id, Date start, Date end)
	{
		this(id);
		setStart(start);
		setEnd(end);
	}

	public Date getStart()
	{
		return window.getStart();
	}

	public Date getEnd()
	{
		return window.getEnd();
	}

	public void setStart(Date start)
	{
		Assert.notNull(start);
		window = new DateWindow(start, window.getEnd());
	}

	public void setEnd(Date end)
	{
		Assert.notNull(end);
		window = new DateWindow(window.getStart(), end);
	}
}