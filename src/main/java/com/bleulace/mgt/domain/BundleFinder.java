package com.bleulace.mgt.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = Bundle.class, idClass = String.class)
public interface BundleFinder
{
	@Query("SELECT b FROM Bundle b WHERE :task MEMBER OF b.tasks")
	public Bundle findOneByTask(@Param("task") Task task);
}
