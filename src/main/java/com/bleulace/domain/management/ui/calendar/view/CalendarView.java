package com.bleulace.domain.management.ui.calendar.view;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.bleulace.domain.management.ui.calendar.component.CalendarComponent.CalendarComponentListener;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.modal.CalendarModal;
import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.bleulace.domain.management.ui.calendar.timeslot.TimeSlotComponent.TimeSlotListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public interface CalendarView extends TimeSlotListener, View
{
	void initialize(CalendarViewContext ctx);

	void setTitle(String title);

	void showTimeSlots(Map<LocalTime, ScheduleStatus> timeSlots);

	public Range<Date> getRange();

	void addViewListener(CalendarViewListener listener);

	void showModalWindow(CalendarViewListener listener, EventModel model);

	public interface CalendarViewListener extends CalendarComponentListener
	{
		void viewEntered(ViewChangeEvent event);

		void timeSlotSelected(LocalTime time, LocalDate start, LocalDate end);

		void applyModal(CalendarModal modal);

		void cancelModal(CalendarModal modal);
	}
}