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
import com.bleulace.domain.management.model.Progress;
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
	private Float progress;

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

	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public boolean isComplete()
	{
		return !(progress < new Float(1));
	}

	@Override
	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setTitle(String title)
	{
		setCaption(title);
	}

	@Override
	public List<UserDTO> getManagers(ManagementLevel level)
	{
		Assert.notNull(level);
		return managers.get(level);
	}

	@Override
	public Float getProgress()
	{
		return progress;
	}

	public void setProgress(Progress progress)
	{
		this.progress = progress.getValue();
	}

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

	private void addManager(UserDTO dto, ManagementLevel level)
	{
		Assert.notNull(dto);
		Assert.notNull(level);
		managers.get(level).add(dto);
	}
}