package com.bleulace.domain.management.ui.calendar.view;

import java.util.Date;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.vaadin.navigator.View;

public interface CalendarView extends View
{
	void addViewListener(CalendarViewListener listener);

	public void setVisibleDates(Date start, Date end);

	void showModal(EventDTO dto, EventDTOCommandCallback<?> callback);

	void refreshCalendar();

	interface EventDTOCommandCallback<T>
	{
		public T getCommand(EventDTO dto);
	}

	public interface CalendarViewListener
	{
		void eventSelected(EventDTO dto);

		void calendarTypeChanged(CalendarType type);

		void eventMoved(EventDTO dto, Date newStart, Date newEnd);

		void rangeSelected(Date start, Date end);
	}
}