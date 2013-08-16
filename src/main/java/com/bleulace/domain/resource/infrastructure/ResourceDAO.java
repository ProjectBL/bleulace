package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.domain.resource.model.Resource;
import com.bleulace.jpa.ReadOnlyDAO;

public interface ResourceDAO extends ReadOnlyDAO<AbstractResource>
{
	@Query("SELECT r FROM AbstractResource r INNER JOIN r.children c "
			+ "WHERE r.id=:id AND TYPE(c)=:clazz")
	public <T extends Resource> List<T> findChildren(@Param("id") String id,
			@Param("clazz") Class<T> clazz);
}