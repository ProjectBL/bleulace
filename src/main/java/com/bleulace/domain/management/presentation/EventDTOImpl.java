package com.bleulace.domain.management.presentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.dto.Mapper;

@Configurable
class EventDTOImpl extends ProjectDTOImpl implements EventDTO
{
	private final Map<String, EventParticipant> participants = new HashMap<String, EventParticipant>();

	EventDTOImpl()
	{
	}

	EventDTOImpl(String id)
	{
		setId(id);
	}

	public void setLocation(String location)
	{
		setDescription(location);
	}

	public void setInvitees(Map<Account, EventInvitee> invitees)
	{
		for (Entry<Account, EventInvitee> entry : invitees.entrySet())
		{
			addInvitee(Mapper.map(entry.getKey(), UserDTO.class), entry
					.getValue().getStatus());
		}
	}

	@Override
	public Map<String, EventParticipant> getParticipants()
	{
		return participants;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof EventDTO)
		{
			EventDTO that = (EventDTO) obj;
			if (this.getId() == null)
			{
				return this == that;
			}
			return this.getId().equals(that.getId());
		}
		return false;
	}

	private void addInvitee(final UserDTO dto, final RsvpStatus status)
	{
		participants.put(dto.getId(), new EventParticipant()
		{
			private ManagementLevel managementLevel = getManagers().get(
					dto.getId());

			@Override
			public UserDTO getUser()
			{
				return dto;
			}

			@Override
			public RsvpStatus getRsvpStatus()
			{
				return status;
			}

			@Override
			public ManagementLevel getManagementLevel()
			{
				return managementLevel;
			}

			@Override
			public void setManagementLevel(ManagementLevel managementLevel)
			{
				this.managementLevel = managementLevel;
			}

		});
	}
}