package com.bleulace.domain.management.command;

import java.util.Date;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.bleulace.jpa.DateWindow;

public class CreateEventCommand extends CreateProjectCommand
{
	@Valid
	private DateWindow window;

	@NotEmpty
	private String location = "";

	public CreateEventCommand(Date start, Date end)
	{
		window = new DateWindow(start, end);
	}

	public CreateEventCommand(String title, String location, Date start,
			Date end)
	{
		this(start, end);
		setTitle(title);
		setLocation(location);
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

	public DateWindow getWindow()
	{
		return window;
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