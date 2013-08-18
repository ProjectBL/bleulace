package com.bleulace.domain.management.ui.calendar;

import org.joda.time.LocalTime;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;

public interface CalendarView
{
	CalendarViewContext getContext();

	void addTimeslot(LocalTime time, ScheduleStatus status);

	void removeTimeslot(LocalTime time);

	void clearTimeslots();

	void setCalendarType(CalendarType type);

	public enum CalendarType
	{
		DAY, WEEK, MONTH;
	}
}