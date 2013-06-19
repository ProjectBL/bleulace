package net.bluelace.ui.web.calendar;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

public interface CalendarView extends View
{
	public void showCenter(String title, Component content);

	public void showEvent(CalendarEvent event);

	public void addViewListener(CalendarViewListener listener);

	public interface CalendarViewListener
	{
		public void onDirectionRequest(RequestDirection direction);

		public void onTabActivated(TabDescriptor descriptor);
	}

	public enum TabDescriptor
	{
		TODAY, WEEK, MONTH, AGENDA;
	}

	public enum RequestDirection
	{
		FORWARD, BACKWARDS;
	}
}
