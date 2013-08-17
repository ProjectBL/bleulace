package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.model.Resource;
import com.bleulace.utils.dto.Mapper;

class ManageableResourceDTOImpl implements ManageableResourceDTO
{
	@NotNull
	private String id;

	@NotNull
	private String caption = "";

	@NotNull
	private String description = "";

	@NotNull
	private final Map<ManagementLevel, List<UserDTO>> managers = new HashMap<ManagementLevel, List<UserDTO>>();

	ManageableResourceDTOImpl()
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
		System.out.println(getClass());
		System.out.println(id);
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
	public void setChildren(List<Resource> resources)
	{
		for (Resource resource : resources)
		{
			if (resource instanceof ManagementAssignment)
			{
				addManager(Mapper.map(
						((ManagementAssignment) resource).getAccount(),
						UserDTO.class),
						((ManagementAssignment) resource).getRole());
			}
		}
	}

	public void setTitle(String title)
	{
		setCaption(title);
	}

	private void addManager(UserDTO dto, ManagementLevel level)
	{
		Assert.notNull(dto);
		Assert.notNull(level);
		managers.get(level).add(dto);
	}
}