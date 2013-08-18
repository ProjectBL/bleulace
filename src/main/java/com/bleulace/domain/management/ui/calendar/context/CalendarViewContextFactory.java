package com.bleulace.domain.management.ui.calendar.context;


public interface CalendarViewContextFactory
{
	public CalendarViewContext make(String ownerId, String viewerId);
}
