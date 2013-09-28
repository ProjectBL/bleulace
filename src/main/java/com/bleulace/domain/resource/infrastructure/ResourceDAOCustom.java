package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.model.AbstractResource;

interface ResourceDAOCustom
{
	public <T extends AbstractResource> List<T> findChildren(String id,
			Class<T> clazz);

	public <T extends AbstractResource> List<T> findByManager(String managerId,
			Class<T> clazz);

	public <T extends AbstractResource> List<T> findByManager(String managerId,
			ManagementLevel level, Class<T> clazz);
}