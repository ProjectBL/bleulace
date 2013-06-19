package net.bluelace.ui.web.calendar;

import net.bluelace.ui.web.calendar.CalendarView.RequestDirection;

import com.vaadin.ui.Calendar;

public interface CalendarContainer
{
	public CalendarContainer forDirection(RequestDirection direction);

	public Calendar getCalendar();
}
