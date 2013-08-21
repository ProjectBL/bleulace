package com.bleulace.domain.management.ui.calendar.component;

import java.util.Date;

import org.apache.commons.lang3.Range;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvent;

aspect CalendarComponentHandlingAspect
{
	public Range<Date> Calendar.getRange()
	{
		return Range.between(new Date(this.getStartDate().getTime()), new Date(
				this.getEndDate().getTime()));
	}

	Object around(CalendarComponentHandlers handlers,
			CalendarComponentEvent event) : 
		execution(* *(..,CalendarComponentEvent+,..)) 
		&& this(handlers)
		&& args(event)
	{
		Calendar calendar = event.getComponent();

		Range<Date> oldDates = getDates(calendar);
		Object retVal = proceed(handlers, event);
		handlers.datesChanged(oldDates, getDates(calendar));

		return retVal;
	}

	private static Range<Date> getDates(Calendar calendar)
	{
		return Range.between(new Date(calendar.getStartDate().getTime()),
				new Date(calendar.getEndDate().getTime()));
	}
}
