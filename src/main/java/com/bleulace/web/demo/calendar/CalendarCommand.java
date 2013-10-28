package com.bleulace.web.demo.calendar;

import com.vaadin.ui.Calendar;

public interface CalendarCommand
{
	void execute(Calendar calendar);

	boolean matches(Calendar calendar);
}