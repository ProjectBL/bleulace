package com.bleulace.mgt.presentation;

import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.Event;
import com.bleulace.utils.dto.BasicFinder;

@Component
public class EventFinderImpl extends BasicFinder<Event, EventDTO> implements
		EventFinder
{
	public EventFinderImpl()
	{
		super(Event.class, EventDTO.class);
	}
}
