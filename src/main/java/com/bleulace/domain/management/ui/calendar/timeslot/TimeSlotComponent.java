package com.bleulace.domain.management.ui.calendar.timeslot;

import java.util.Map;

import org.joda.time.LocalTime;

import com.bleulace.domain.management.presentation.ScheduleStatus;

public interface TimeSlotComponent
{
	public void showTimeSlots(Map<LocalTime, ScheduleStatus> timeSlots);

	public void clearTimeSlots();

	public void addListener(TimeSlotListener listener);

	public interface TimeSlotListener
	{
		void timeSelected(LocalTime time);
	}
}
