package com.bleulace.domain.management.ui.calendar;

import com.bleulace.domain.management.ui.calendar.CalendarView.CalendarType;

public class CalendarTypeSelectedEvent
{
	private final CalendarView view;

	private final CalendarType type;

	public CalendarTypeSelectedEvent(CalendarView view, CalendarType type)
	{
		this.view = view;
		this.type = type;
	}

	public CalendarView getView()
	{
		return view;
	}

	public CalendarType getType()
	{
		return type;
	}
}
