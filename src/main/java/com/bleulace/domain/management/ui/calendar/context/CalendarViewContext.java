package com.bleulace.domain.management.ui.calendar.context;

import java.io.Serializable;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public interface CalendarViewContext extends Serializable
{
	public String getOwnerId();

	public String getViewerId();

	public void decorate(EventDTO dto);

	public CalendarEventProvider getEventProvider();
}