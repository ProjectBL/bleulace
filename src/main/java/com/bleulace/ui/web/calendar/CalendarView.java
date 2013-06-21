package com.bleulace.ui.web.calendar;

import java.util.Date;

import com.bleulace.domain.calendar.CalendarEntry;
import com.vaadin.navigator.View;

public interface CalendarView extends View
{
	/**
	 * Activate a calendar type (day,week,month)
	 * 
	 * @param CalendarType
	 *            to Activate
	 */
	public void setActiveTab(CalendarType type);

	/**
	 * Set calendar endpoints. Dates outside start and end are therefore not
	 * visible.
	 * 
	 * @param start
	 * @param end
	 */
	public void setBoundaries(Date start, Date end);

	/**
	 * Add a CalendarViewListener
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addViewListener(CalendarViewListener listener);

	/**
	 * 
	 * @param entry
	 *            to show details
	 * @param editable
	 *            whether or not entry will be allowed to be edited
	 */
	public void showEntryDetail(CalendarEntry entry, Boolean editable);

	/**
	 * makes entry detail view invisible
	 */
	public void hideEntryDetail();

	/**
	 * adds an entry to calendar. persistent datastore will be updated.
	 * 
	 * @param entry
	 *            to add
	 */
	public void addEntryToCalendar(CalendarEntry entry);

	public interface CalendarViewListener
	{
		/**
		 * called when a new tab is selected
		 * 
		 * @param type
		 */
		public void onTabSelected(CalendarType type);

		/**
		 * called when new calendar boundaries are selected
		 * 
		 * @param start
		 * @param end
		 */
		public void onBoundariesSelected(Date start, Date end);

		/**
		 * called when a date range is selected in calendar
		 * 
		 * @param start
		 * @param end
		 */
		public void onRangeSelected(Date start, Date end);

		/**
		 * called when a date is clicked on
		 * 
		 * @param date
		 */
		public void onDateClicked(Date date);

		/**
		 * called when a calendar entry is selected
		 * 
		 * @param selected
		 */
		public void onEntrySelected(CalendarEntry selected);
	}
}