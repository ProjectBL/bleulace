package com.bleulace.mgt.presentation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.Event;
import com.bleulace.mgt.domain.EventDAO;
import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.dto.BasicFinder;

@Component
public class EventFinderImpl extends BasicFinder<Event, EventDTO> implements
		EventFinder
{
	@Autowired
	private EventDAO eventDAO;

	public EventFinderImpl()
	{
		super(Event.class, EventDTO.class);
	}

	@Override
	public List<EventDTO> findByAssignment(String accountId,
			ManagementAssignment assignment)
	{
		return getConverter().convert(
				eventDAO.findByAssignment(accountId, assignment));
	}

	@Override
	public List<EventDTO> findByAccountIdAndRange(String accountId, Date start,
			Date end)
	{
		return getConverter().convert(
				eventDAO.findByByAttendeeAndDates(accountId, start, end));
	}
}