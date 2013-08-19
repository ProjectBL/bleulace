package com.bleulace.domain.management.ui.calendar.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.components.calendar.event.CalendarEditableEventProvider;

@Configurable
class SelfCalendarViewContext implements CalendarViewContext
{
	private final String ownerId;

	SelfCalendarViewContext(String ownerId)
	{
		this.ownerId = ownerId;
	}

	@Override
	public String getOwnerId()
	{
		return ownerId;
	}

	@Override
	public String getViewerId()
	{
		return ownerId;
	}

	@Override
	public void decorate(EventDTO dto)
	{
		dto.setStyleName(dto.getRsvpStatus(ownerId).getStyleName());
	}

	@Override
	public CalendarEditableEventProvider getEventProvider()
	{
		return new EventDTOProvider(this);
	}
}
