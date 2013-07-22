package com.bleulace.mgt.application.command;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.utils.jpa.LocalDateTimeRange;

@RooJavaBean
public class CreateEventCommand extends CreateProjectCommand
{
	@NotNull
	private String location = "";

	private LocalDateTimeRange range = LocalDateTimeRange.defaultValue();

	public Date getStart()
	{
		return range.getStart();
	}

	public Date getEnd()
	{
		return range.getEnd();
	}

	public void setStart(Date start)
	{
		range = range.withStart(start);
	}

	public void setEnd(Date end)
	{
		range = range.withEnd(end);
	}
}