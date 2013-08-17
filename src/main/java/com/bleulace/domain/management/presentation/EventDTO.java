package com.bleulace.domain.management.presentation;

import java.util.List;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface EventDTO extends ProjectDTO, EditableCalendarEvent,
		EventChangeNotifier
{
	public List<UserDTO> getInvitees(RsvpStatus status);

	public void addInvitee(UserDTO dto, RsvpStatus status);
}