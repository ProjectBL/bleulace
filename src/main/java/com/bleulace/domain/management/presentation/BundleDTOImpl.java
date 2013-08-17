package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.List;

import com.bleulace.domain.management.model.Task;
import com.bleulace.domain.resource.model.Resource;
import com.bleulace.utils.dto.Mapper;

class BundleDTOImpl extends ProjectDTOImpl implements BundleDTO
{
	private List<TaskDTO> tasks = new ArrayList<TaskDTO>();

	@Override
	public void setChildren(List<Resource> resources)
	{
		super.setChildren(resources);
		for (Resource resource : resources)
		{
			if (resource instanceof Task)
			{
				tasks.add(Mapper.map(resource, TaskDTO.class));
			}
		}
	}

	public List<TaskDTO> getTasks()
	{
		return tasks;
	}
}
