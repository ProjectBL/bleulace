package com.bleulace.domain.management.presentation;

import java.util.Map;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface EventDTO extends ProjectDTO, EditableCalendarEvent,
		EventChangeNotifier
{
	public Map<String, EventParticipant> getParticipants();

	public interface EventParticipant
	{
		public UserDTO getUser();

		public RsvpStatus getRsvpStatus();

		public ManagementLevel getManagementLevel();

		public void setManagementLevel(ManagementLevel managementLevel);
	}
}