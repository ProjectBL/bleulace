package com.bleulace.mgt.application.command;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.utils.jpa.DateWindow;

@RooJavaBean
public class CreateEventCommand extends CreateProjectCommand
{
	@NotNull
	private String location = "";

	private DateWindow window = DateWindow.defaultValue();

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
		window = window.withStart(start);
	}

	public void setEnd(Date end)
	{
		window = window.withEnd(end);
	}
}