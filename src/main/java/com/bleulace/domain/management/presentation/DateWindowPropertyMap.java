package com.bleulace.domain.management.presentation;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.bleulace.jpa.DateWindow;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

@Component
class DateWindowPropertyMap extends
		PropertyMap<DateWindow, EditableCalendarEvent>
{
	public DateWindowPropertyMap()
	{
		super(DateWindow.class, EditableCalendarEvent.class);
	}

	@Override
	protected void configure()
	{
		map().setStart(source.getStart());
		map().setEnd(source.getEnd());
	}
}
