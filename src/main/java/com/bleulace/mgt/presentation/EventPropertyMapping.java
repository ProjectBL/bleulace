package com.bleulace.mgt.presentation;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.Event;

@Component
public class EventPropertyMapping extends PropertyMap<Event, EventDTO>
{
	@Override
	protected void configure()
	{
		map().setCaption(source.getTitle());
		map().setDescription(source.getLocation());
	}
}