package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.RsvpStatus;

class EventDTOImpl extends ProjectDTOImpl implements EventDTO
{
	private final Map<RsvpStatus, List<UserDTO>> invitees = new HashMap<RsvpStatus, List<UserDTO>>();

	EventDTOImpl()
	{
		for (RsvpStatus status : RsvpStatus.values())
		{
			invitees.put(status, new ArrayList<UserDTO>());
		}
	}

	@Override
	public List<UserDTO> getInvitees(RsvpStatus status)
	{
		Assert.notNull(status);
		return invitees.get(status);
	}

	@Override
	public void addInvitee(UserDTO dto, RsvpStatus status)
	{
		Assert.notNull(dto);
		Assert.notNull(status);
		invitees.get(status).add(dto);
	}

	public void addInvitees(Iterable<UserDTO> dtos, RsvpStatus status)
	{
		for (UserDTO dto : dtos)
		{
			addInvitee(dto, status);
		}
	}
}