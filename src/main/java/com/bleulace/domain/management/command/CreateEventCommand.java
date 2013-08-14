package com.bleulace.domain.management.command;

import java.util.Date;

import javax.validation.Valid;

import com.bleulace.jpa.DateWindow;

public class CreateEventCommand extends CreateProjectCommand
{
	@Valid
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
		window = new DateWindow(start, window.getEnd());
	}

	public void setEnd(Date end)
	{
		window = new DateWindow(window.getStart(), end);
	}
}