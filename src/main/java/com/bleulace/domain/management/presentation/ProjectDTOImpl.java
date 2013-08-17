package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;

class ProjectDTOImpl implements ProjectDTO
{
	@NotNull
	private String id;

	@NotNull
	private String caption = "";

	@NotNull
	private String description = "";

	@NotNull
	private final Map<ManagementLevel, List<UserDTO>> managers = new HashMap<ManagementLevel, List<UserDTO>>();

	ProjectDTOImpl()
	{
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
	public String getCaption()
	{
		return caption;
	}

	@Override
	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public void setDescription(String description)
	{
		this.description = description;
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
