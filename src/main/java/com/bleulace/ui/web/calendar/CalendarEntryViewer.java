package com.bleulace.ui.web.calendar;

import com.bleulace.domain.calendar.CalendarEntry;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class CalendarEntryViewer extends AbstractCalendarEntryDetail
{
	private static final long serialVersionUID = -4206862826575716813L;

	private CalendarEntry entry;

	public CalendarEntryViewer()
	{
	}

	@Override
	public void showEntry(CalendarEntry entry)
	{
		this.entry = entry;
		VerticalLayout layout = new VerticalLayout(
				new Label(entry.getCaption()),
				new Label(entry.getDescription()), new Label(
						entry.getLocation()));
		setCompositionRoot(layout);
	}
}