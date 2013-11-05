package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.resource.model.AbstractResource;

interface ResourceDAOCustom
{
	public List<String> findIdsForManager(String managerId);

	public List<Account> findManagers(AbstractResource resource);
}