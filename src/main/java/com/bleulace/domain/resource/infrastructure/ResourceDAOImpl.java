package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.management.model.QManager;
import com.bleulace.domain.resource.model.QAbstractResource;
import com.bleulace.jpa.config.QueryFactory;

class ResourceDAOImpl implements ResourceDAOCustom
{
	private final QAbstractResource r = new QAbstractResource("r");
	private final QManager a = new QManager("a");

	@Override
	public List<String> findIdsForManager(String managerId)
	{
		return QueryFactory.from(r).innerJoin(r.managers, a)
				.where(a.account.id.eq(managerId)).list(r.id);
	}
}