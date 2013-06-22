package com.bleulace.ui.web.calendar;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.calendar.CalendarEntry;
import com.bleulace.ui.web.calendar.CalendarEntryDetail.CalendarEntryDetailListener;
import com.bleulace.ui.web.calendar.CalendarView.CalendarViewListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@Configurable
public class CalendarPresenter implements CalendarViewListener,
		CalendarEntryDetailListener, Serializable
{
	private static final long serialVersionUID = -8809727082371971055L;

	private final CalendarView view;

	private CalendarEntry entry;

	public CalendarPresenter(CalendarView view)
	{
		this.view = view;
		Date now = new Date();
		onBoundariesSelected(now, now);
	}

	@Override
	public void onTabSelected(CalendarType type)
	{
		Range<Date> range = type.getDateRange(new Date());
		onBoundariesSelected(range.getMinimum(), range.getMaximum());
		view.setActiveTab(type);
		view.hideEntryDetail();
	}

	@Override
	public void onBoundariesSelected(Date start, Date end)
	{
		if (end.before(start))
		{
			end = start;
		}
		view.setBoundaries(start, end);
	}

	@Override
	public void onDateClicked(Date date)
	{
		if (entry != null && !entry.getStart().equals(date))
		{
			view.hideEntryDetail();
		}
	}

	@Override
	public void onEntrySelected(CalendarEntry selected)
	{
		entry = selected;
		view.setBoundaries(entry.getStart(), entry.getEnd());
		view.setActiveTab(null);
		view.showEntryDetail(selected, true);
	}

	@Override
	public void onRangeSelected(Date start, Date end)
	{
		CalendarEntry entry = new CalendarEntry();
		entry.setStart(start);
		entry.setEnd(end);
		onEntrySelected(entry);
	}

	// ----------------------------------------------------

	@Override
	@Transactional
	public void onSaveButtonClicked(CalendarEntry entry)
	{
		view.setBoundaries(entry.getStart(), entry.getEnd());
		view.addEntryToCalendar(entry);
		view.hideEntryDetail();
		Notification.show("Event: " + entry.getCaption() + " saved.",
				Type.TRAY_NOTIFICATION);
	}

	@Override
	public void onCancelButtonClicked()
	{
		view.hideEntryDetail();
		Notification.show("Event: " + entry.getCaption() + "not saved.",
				Type.TRAY_NOTIFICATION);
	}
}