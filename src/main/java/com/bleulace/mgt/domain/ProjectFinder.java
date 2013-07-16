package com.bleulace.mgt.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = Project.class, idClass = String.class)
public interface ProjectFinder
{
	@Query("SELECT p.id FROM Project p JOIN p.bundles b WHERE b.id=:bundleId")
	public String findIdFromBundleId(@Param("bundleId") String bundleId);
}