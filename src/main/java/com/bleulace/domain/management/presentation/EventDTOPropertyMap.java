package com.bleulace.domain.management.presentation;

import org.modelmapper.PropertyMap;

import com.bleulace.domain.management.model.Event;

public class EventDTOPropertyMap extends PropertyMap<Event, EventDTO>
{
	public EventDTOPropertyMap()
	{
		super(Event.class, EventDTO.class);
	}

	@Override
	protected void configure()
	{
		map().setDescription(source.getLocation());
	}
}
