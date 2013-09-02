package com.bleulace.domain.management.presentation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.dto.Mapper;

@Configurable
class EventDTOImpl extends ProjectDTOImpl implements EventDTO
{
	private final Map<UserDTO, RsvpStatus> invitees = new HashMap<UserDTO, RsvpStatus>();

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
	public Map<UserDTO, RsvpStatus> getInvitees()
	{
		return invitees;
	}

	@Override
	public Map<String, UserDTO> getInviteeIds(RsvpStatus... statuses)
	{
		Map<String, UserDTO> map = new HashMap<String, UserDTO>();
		List<RsvpStatus> statusList = Arrays.asList(statuses);
		for (Entry<UserDTO, RsvpStatus> entry : invitees.entrySet())
		{
			if (statusList.contains(entry.getValue()))
			{
				map.put(entry.getKey().getId(), entry.getKey());
			}
		}
		return map;
	}

	@Override
	public Set<String> getInviteeIds()
	{
		return getInviteeIds(RsvpStatus.values()).keySet();
	}

	@Override
	public RsvpStatus getRsvpStatus(String accountId)
	{
		return invitees.get(getInviteeIds(RsvpStatus.values()).get(accountId));
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

	private void addInvitee(UserDTO dto, RsvpStatus status)
	{
		Assert.notNull(dto);
		Assert.notNull(status);
		invitees.put(dto, status);
	}
}