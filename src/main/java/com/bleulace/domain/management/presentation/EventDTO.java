package com.bleulace.domain.management.presentation;

import java.util.List;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface EventDTO extends EditableCalendarEvent
{
	public String getId();

	public void setId(String id);

	public List<UserDTO> getInvitees(RsvpStatus status);

	public void addInvitee(UserDTO dto, RsvpStatus status);

	public List<UserDTO> getManagers(ManagementLevel level);

	public void addManager(UserDTO dto, ManagementLevel level);
}