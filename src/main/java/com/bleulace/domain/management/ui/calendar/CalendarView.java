package com.bleulace.domain.management.ui.calendar;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.joda.time.LocalTime;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.vaadin.ui.Calendar;

public interface CalendarView
{
	void setTitle(String title);

	void setCalendar(Calendar calendar);

	void addTimeSlot(LocalTime time, ScheduleStatus status);

	void removeTimeSlot(LocalTime time);

	void clearTimeslots();

	Range<Date> getDates();

	void setDates(Range<Date> dates);

	void addViewListener(CalendarViewListener listener);

	public interface CalendarViewListener
	{
		void viewEntered(String params);

		void timeslotSelected(TimeSlot time);

		void calendarTypeSelected(CalendarType type);
	}
}