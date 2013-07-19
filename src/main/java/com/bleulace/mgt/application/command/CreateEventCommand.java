package com.bleulace.mgt.application.command;

import java.util.Date;

import com.bleulace.mgt.domain.DateRange;
import com.bleulace.mgt.domain.FutureRange;

public class CreateEventCommand extends CreateProjectCommand
{
	@FutureRange
	private DateRange range = new DateRange();

	public CreateEventCommand()
	{
	}

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
		range.setStart(start);
	}

	public void setEnd(Date end)
	{
		range.setEnd(end);
	}
}