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

	public List<String> findManagerIds(String id, ManagementLevel... levels);

	public ManagementLevel findManagementLevel(String resourceId,
			String accountId);
}