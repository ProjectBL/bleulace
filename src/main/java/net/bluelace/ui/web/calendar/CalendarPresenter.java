package net.bluelace.ui.web.calendar;

import java.io.Serializable;
import java.util.Date;

import net.bluelace.domain.account.Account;
import net.bluelace.domain.calendar.BLCalendarEventProvider;
import net.bluelace.ui.web.calendar.CalendarType.RequestDirection;

import org.apache.commons.lang3.Range;

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