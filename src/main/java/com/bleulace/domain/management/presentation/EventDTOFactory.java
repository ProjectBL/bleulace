package com.bleulace.domain.management.presentation;

import com.bleulace.utils.dto.DTOFactory;
import com.bleulace.utils.dto.Factory;

@Factory(makes = EventDTO.class)
public class EventDTOFactory implements DTOFactory<EventDTO>
{
	@Override
	public EventDTO make()
	{
		return new EventDTOImpl();
	}

	public EventDTO make(String id)
	{
		return new EventDTOImpl(id);
	}
}