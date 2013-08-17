package com.bleulace.domain.management.presentation;

import com.bleulace.utils.dto.DTOFactory;
import com.bleulace.utils.dto.Factory;

@Factory(makes = ProjectDTO.class)
class ProjectDTOFactory implements DTOFactory<ProjectDTO>
{
	@Override
	public ProjectDTO make()
	{
		return new ProjectDTOImpl();
	}
}