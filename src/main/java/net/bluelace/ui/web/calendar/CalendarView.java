package net.bluelace.ui.web.calendar;

import net.bluelace.ui.web.calendar.CalendarType.RequestDirection;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

public interface CalendarView extends View
{
	public void showTitle(String title);

	public void showMainContent(Component mainContent);

	public void showEvent(CalendarEvent event);

	public void addViewListener(CalendarViewListener listener);

	public interface CalendarViewListener
	{
		public void onDirectionRequest(RequestDirection direction);

		public void onTabActivated(CalendarType type);
	}
}