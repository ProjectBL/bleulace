package com.bleulace.mgt.presentation;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.Event;
import com.bleulace.mgt.domain.EventDAO;
import com.bleulace.mgt.domain.EventInvitation;
import com.bleulace.mgt.domain.EventInvitationDAO;
import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.dto.BasicFinder;
import com.bleulace.utils.dto.DTOConverter;
import com.bleulace.utils.dto.ModelMappingDTOConverter;

@Component
public class EventFinderImpl extends BasicFinder<Event, EventDTO> implements
		EventFinder
{
	private final DTOConverter<EventInvitation, EventDTO> invitationConverter = new ModelMappingDTOConverter<EventInvitation, EventDTO>(
			EventDTO.class);

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private EventInvitationDAO invitationDAO;

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
		List<EventDTO> events = getConverter().convert(
				eventDAO.findByByAttendeeAndDates(accountId, start, end));
		events.addAll(getConverter().convert(
				invitationDAO.findByByAttendeeAndDates(accountId, start, end)));
		Collections.sort(events, new Comparator<EventDTO>()
		{
			@Override
			public int compare(EventDTO o1, EventDTO o2)
			{
				return o1.getStart().compareTo(o2.getStart());
			}
		});
		return events;
	}
}