package com.bleulace.web.demo.calendar.span;

import com.vaadin.ui.Calendar;

public enum CalendarSpan implements CalendarSpanCallback
{
	//@formatter:off
		DAY("Day",new DaySpan()), 
		WEEK("Week",new WeekSpan()), 
		MONTH("Month",new MonthSpan());
	//@formatter:on

	final String selectionName;
	final CalendarSpanCallback command;

	CalendarSpan(String selectionName, CalendarSpanCallback command)
	{
		this.selectionName = selectionName;
		this.command = command;
	}

	@Override
	public String toString()
	{
		return selectionName;
	}

	@Override
	public void resize(Calendar calendar)
	{
		command.resize(calendar);
		calendar.markAsDirty();
	}
}