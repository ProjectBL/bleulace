package com.bleulace.domain.resource.infrastructure;

import java.util.List;

interface ResourceDAOCustom
{
	public List<String> findIdsForManager(String managerId);
}