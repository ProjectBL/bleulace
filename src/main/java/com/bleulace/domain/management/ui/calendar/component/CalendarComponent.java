package com.bleulace.domain.management.ui.calendar.component;

import java.util.Date;

import org.apache.commons.lang3.Range;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;

public interface CalendarComponent
{
	public void initialize(CalendarViewContext ctx);

	public Range<Date> getRange();

	public void addListener(CalendarComponentListener listener);

	public void removeListener(CalendarComponentListener listener);

	public interface CalendarComponentListener
	{
		void visibleDatesChange(Date oldStart, Date oldEnd, Date newStart,
				Date newEnd);

		void eventSelected(EventDTO dto);

		void eventDragged(EventDTO dto, Date newStart, Date newEnd);

		void rangeSelected(Date start, Date end);
	}
}
