package com.bleulace.mgt.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.Event;
import com.bleulace.mgt.domain.EventDAO;
import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.dto.BasicFinder;
import com.bleulace.utils.dto.DTOConverter;

@Component
public class EventFinderImpl extends BasicFinder<Event, EventDTO> implements
		EventFinder
{
	@Autowired
	private EventDAO eventDAO;

	public EventFinderImpl()
	{
		super(Event.class, EventDTO.class);
		setConverter(new EventConverter());
	}

	@Override
	public List<EventDTO> findByAssignment(String accountId,
			ManagementAssignment assignment)
	{
		return getConverter().convert(
				eventDAO.findByAssignment(accountId, assignment));
	}

	static class EventConverter extends DTOConverter<Event, EventDTO>
	{
		@Override
		public EventDTO convert(Event source)
		{
			EventDTO dto = new EventDTO();
			dto.setTitle(source.getTitle());
			dto.setStart(source.getStart().toDate());
			dto.setEnd(source.getStart().plus(source.getLength()).toDate());
			dto.setCaption(source.getTitle());
			dto.setDescription(source.getLocation());
			return dto;
		}
	}
}