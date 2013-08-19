package com.bleulace.domain.management.ui.calendar;

import org.joda.time.LocalTime;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

public interface CalendarView
{
	CalendarViewContext getContext();

	void setTitle(String title);

	void addTimeSlot(LocalTime time, ScheduleStatus status);

	void removeTimeSlot(LocalTime time);

	void clearTimeslots();

	void setCalendarType(CalendarType type);

	void addViewListener(CalendarViewListener listener);

	public enum CalendarType
	{
		DAY, WEEK, MONTH;
	}

	public interface CalendarViewListener extends DateClickHandler,
			RangeSelectHandler, ForwardHandler, BackwardHandler
	{
		void timeslotSelect(TimeSlot time);

		void calendarTypeSelect(CalendarType type);
	}
}