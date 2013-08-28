package com.bleulace.domain.management.presentation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.Event;
import com.bleulace.utils.dto.AbstractFinder;

@Component
class EventFinderImpl extends AbstractFinder<Event, EventDTO> implements
		EventFinder
{
	EventFinderImpl()
	{
		super(Event.class, EventDTO.class);
	}

	@Autowired
	private EventDAO dao;

	@Override
	public List<EventDTO> findByAccountIdForDates(String accountId, Date start,
			Date end)
	{
		return convert(dao.findEvents(start, end, accountId));
	}
}
