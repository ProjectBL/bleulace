package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.management.model.QManagementAssignment;
import com.bleulace.domain.resource.model.QAbstractResource;
import com.bleulace.jpa.config.QueryFactory;

@SuppressWarnings("unchecked")
class ResourceDAOImpl implements ResourceDAOCustom
{
	private final QAbstractResource r = new QAbstractResource("r");
	private final QAbstractResource c = new QAbstractResource("c");
	private final QManagementAssignment a = new QManagementAssignment("a");

	@Override
	public List<String> findIdsForManager(String managerId)
	{
		return QueryFactory.from(r).innerJoin(r.assignments, a)
				.where(a.account.id.eq(managerId)).list(r.id);
	}
}