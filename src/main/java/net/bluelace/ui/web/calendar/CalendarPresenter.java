package net.bluelace.ui.web.calendar;

import java.io.Serializable;

import net.bluelace.ui.web.calendar.CalendarView.RequestDirection;
import net.bluelace.ui.web.calendar.CalendarView.TabDescriptor;

public class CalendarPresenter implements CalendarView.CalendarViewListener,
		Serializable
{
	private static final long serialVersionUID = -8809727082371971055L;

	private final CalendarView view;

	public CalendarPresenter(CalendarView view)
	{
		this.view = view;
	}

	@Override
	public void onDirectionRequest(RequestDirection direction)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabActivated(TabDescriptor descriptor)
	{
		// TODO Auto-generated method stub

	}
}
