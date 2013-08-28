package com.bleulace.domain.management.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.Project;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.utils.dto.AbstractFinder;

@Component
class ProjectFinderImpl extends AbstractFinder<Project, ProjectDTO> implements
		ProjectFinder
{
	@Autowired
	private ResourceDAO dao;

	public ProjectFinderImpl()
	{
		super(Project.class, ProjectDTO.class);
	}

	@Override
	public List<ProjectDTO> findByManager(String accountId)
	{
		return convert(dao.findByManager(accountId, Project.class));
	}
}
