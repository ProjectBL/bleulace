package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.ui.components.calendar.event.BasicEvent;

public class EventDTOImpl extends BasicEvent implements EventDTO
{
	private String id;

	private final Map<RsvpStatus, List<UserDTO>> invitees = new HashMap<RsvpStatus, List<UserDTO>>();

	private final Map<ManagementLevel, List<UserDTO>> managers = new HashMap<ManagementLevel, List<UserDTO>>();

	public EventDTOImpl()
	{
		for (RsvpStatus status : RsvpStatus.values())
		{
			invitees.put(status, new ArrayList<UserDTO>());
		}

		for (ManagementLevel level : ManagementLevel.values())
		{
			managers.put(level, new ArrayList<UserDTO>());
		}
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void setId(String id)
	{
		Assert.notNull(id);
		this.id = id;
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

	@Override
	public List<UserDTO> getManagers(ManagementLevel level)
	{
		Assert.notNull(level);
		return managers.get(level);
	}

	@Override
	public void addManager(UserDTO dto, ManagementLevel level)
	{
		Assert.notNull(dto);
		Assert.notNull(level);
		managers.get(level).add(dto);
	}

	public void addManagers(Iterable<UserDTO> dtos, ManagementLevel level)
	{
		for (UserDTO dto : dtos)
		{
			addManager(dto, level);
		}
	}
}
