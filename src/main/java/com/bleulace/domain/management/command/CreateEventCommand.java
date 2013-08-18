package com.bleulace.domain.management.command;

import java.util.Date;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.bleulace.jpa.DateWindow;

public class CreateEventCommand extends CreateProjectCommand
{
	@Valid
	private DateWindow window = DateWindow.defaultValue();

	@NotEmpty
	private String location = "";

	public CreateEventCommand()
	{
	}

	public CreateEventCommand(String title, String location, Date start,
			Date end)
	{
		setTitle(title);
		setLocation(location);
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
		window = new DateWindow(start, window.getEnd());
	}

	public void setEnd(Date end)
	{
		window = new DateWindow(window.getStart(), end);
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
}