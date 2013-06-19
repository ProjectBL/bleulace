package net.bluelace.ui.web.rubiks;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

public interface CalendarView extends View
{
	public void showCenterContent(String title, Component component);

	public interface CalendarViewListener
	{
		public void onTodayClicked();

		public void onWeekClicked();

		public void onMonthClicked();

		public void onAgendaClicked();

		public void onBundleClicked();

		public void onIncrement();

		public void onDecrement();
	}
}