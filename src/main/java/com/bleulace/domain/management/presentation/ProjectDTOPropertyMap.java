package com.bleulace.domain.management.presentation;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.Project;

@Component
class ProjectDTOPropertyMap extends PropertyMap<Project, ProjectDTO>
{
	public ProjectDTOPropertyMap()
	{
		super(Project.class, ProjectDTO.class);
	}

	@Override
	protected void configure()
	{
		map().setCaption(source.getTitle());
	}
}