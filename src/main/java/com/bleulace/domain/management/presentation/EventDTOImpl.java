package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.domain.resource.model.Resource;
import com.bleulace.utils.dto.Mapper;

class EventDTOImpl extends ProjectDTOImpl implements EventDTO
{
	private final Map<RsvpStatus, List<UserDTO>> invitees = new HashMap<RsvpStatus, List<UserDTO>>();

	private final Map<String, RsvpStatus> rsvpStatuses = new HashMap<String, RsvpStatus>();

	EventDTOImpl()
	{
		for (RsvpStatus status : RsvpStatus.values())
		{
			invitees.put(status, new ArrayList<UserDTO>());
		}
	}

	public void setLocation(String location)
	{
		setDescription(location);
	}

	public RsvpStatus getRsvpStatus(String accountId)
	{
		return rsvpStatuses.get(accountId);
	}

	@Override
	public List<UserDTO> getInvitees(RsvpStatus status)
	{
		Assert.notNull(status);
		return invitees.get(status);
	}

	@Override
	public void setChildren(List<Resource> resources)
	{
		super.setChildren(resources);
		for (Resource resource : resources)
		{
			if (resource instanceof EventInvitee)
			{
				EventInvitee invitee = (EventInvitee) resource;
				addInvitee(Mapper.map(invitee.getGuest(), UserDTO.class),
						invitee.getStatus());
			}
		}
	}

	private void addInvitee(UserDTO dto, RsvpStatus status)
	{
		Assert.notNull(dto);
		Assert.notNull(status);
		invitees.get(status).add(dto);
		rsvpStatuses.put(dto.getId(), status);
	}
}