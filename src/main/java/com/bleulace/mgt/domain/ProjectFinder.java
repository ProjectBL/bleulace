package com.bleulace.mgt.domain;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Project.class, idClass = String.class)
public interface ProjectFinder
{
	public Project findById(String id);

	public Project findAll();
}