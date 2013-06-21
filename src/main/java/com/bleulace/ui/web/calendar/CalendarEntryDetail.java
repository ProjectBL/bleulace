package com.bleulace.ui.web.calendar;

import com.bleulace.domain.calendar.CalendarEntry;

public interface CalendarEntryDetail
{
	public void showEntry(CalendarEntry entry);

	public void addListener(CalendarEntryDetailListener listener);

	public interface CalendarEntryDetailListener
	{
		public void onSaveButtonClicked(CalendarEntry entry);

		public void onCancelButtonClicked();
	}
}