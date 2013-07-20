package com.bleulace.mgt.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface BundleDAO extends ReadOnlyDAO<Bundle, String>
{
	@Query("SELECT b FROM Bundle b WHERE b.parent.id=:parentId")
	public List<Bundle> findByParentId(@Param("parentId") String parentId);

	@Query("SELECT b FROM Bundle b JOIN b.tasks t WHERE t.id=:taskId")
	public Bundle findByTaskId(@Param("taskId") String taskId);
}