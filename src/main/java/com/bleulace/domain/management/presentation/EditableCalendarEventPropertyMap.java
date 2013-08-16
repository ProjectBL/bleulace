package com.bleulace.domain.management.presentation;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.Event;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

@Component
class EditableCalendarEventPropertyMap extends
		PropertyMap<Event, EditableCalendarEvent>
{
	public EditableCalendarEventPropertyMap()
	{
		super(Event.class, EditableCalendarEvent.class);
	}

	@Override
	protected void configure()
	{
		map().setCaption(source.getTitle());
		map().setDescription(source.getLocation());
	}
}