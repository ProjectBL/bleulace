package com.bleulace.ui.web.calendar;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.Range;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.BLCalendarEventProvider;
import com.bleulace.ui.web.calendar.CalendarType.RequestDirection;
import com.vaadin.ui.Calendar;

public class CalendarPresenter implements CalendarView.CalendarViewListener,
		Serializable
{
	private static final long serialVersionUID = -8809727082371971055L;

	private final CalendarView view;

	private Date cursor = new Date();

	private CalendarType type = CalendarType.DAY;

	public CalendarPresenter(CalendarView view)
	{
		this.view = view;
		updateView();
	}

	@Override
	public void onDirectionRequest(RequestDirection direction)
	{
		cursor = type.moveCursor(cursor, direction);
		updateView();
	}

	@Override
	public void onTabActivated(CalendarType type)
	{
		this.type = type;
		updateView();
	}

	private void updateView()
	{
		view.showTitle(type.getTitle(cursor));
		view.showMainContent(makeCalendar());
	}

	private Calendar makeCalendar()
	{
		Range<Date> range = type.getDateRange(cursor);
		Calendar calendar = new Calendar(new BLCalendarEventProvider(
				Account.current()));
		calendar.setStartDate(range.getMinimum());
		calendar.setEndDate(range.getMaximum());
		return calendar;
	}
}