package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.resource.model.AbstractChildResource;

interface ResourceDAOCustom
{
	public <T extends AbstractChildResource> List<T> findChildren(String id,
			Class<T> clazz);
}