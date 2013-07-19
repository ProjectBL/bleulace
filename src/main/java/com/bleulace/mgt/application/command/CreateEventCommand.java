package com.bleulace.mgt.application.command;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

public class CreateEventCommand extends CreateProjectCommand
{
	private Range<Date> range;

	public CreateEventCommand()
	{
		initializeRange();
	}

	private void initializeRange()
	{
		range = Range.between(DateTime.now().plusMinutes(15).toDate(), DateTime
				.now().plusMinutes(75).toDate());
	}

	public Date getStart()
	{
		return range.getMinimum();
	}

	public void setStart(Date start)
	{
		Assert.notNull(start);
		range = Range.between(start, getEnd());
	}

	public Date getEnd()
	{
		return range.getMaximum();
	}

	public void setEnd(Date end)
	{
		Assert.notNull(end);
		range = Range.between(getStart(), end);
	}
}