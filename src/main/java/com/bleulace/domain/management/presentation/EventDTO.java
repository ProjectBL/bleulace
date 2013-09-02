package com.bleulace.domain.management.presentation;

import java.util.Map;
import java.util.Set;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface EventDTO extends ProjectDTO, EditableCalendarEvent,
		EventChangeNotifier
{
	public Map<UserDTO, RsvpStatus> getInvitees();

	public Set<String> getInviteeIds();

	public Map<String, UserDTO> getInviteeIds(RsvpStatus... status);

	public RsvpStatus getRsvpStatus(String accountId);
}