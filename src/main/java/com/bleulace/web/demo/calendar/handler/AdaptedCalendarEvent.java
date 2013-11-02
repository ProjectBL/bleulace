package com.bleulace.web.demo.calendar.handler;

import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface AdaptedCalendarEvent extends EditableCalendarEvent,
		EventChangeNotifier
{
	public String getId();

	public RsvpStatus getRsvpStatus(String accountId);

	public void setRsvpStatus(String accountId, RsvpStatus status);

	public ManagementLevel getManagementLevel(String accountId);

	public void setManagementLevel(String accountId, ManagementLevel level);

	public AdaptedCalendarEvent save();

	public void delete();
}