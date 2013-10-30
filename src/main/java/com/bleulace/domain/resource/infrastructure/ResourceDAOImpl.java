package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.management.model.QManagementAssignment;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.domain.resource.model.QAbstractResource;
import com.bleulace.jpa.config.QueryFactory;

@SuppressWarnings("unchecked")
class ResourceDAOImpl implements ResourceDAOCustom
{
	private final QAbstractResource r = new QAbstractResource("r");
	private final QAbstractResource c = new QAbstractResource("c");
	private final QManagementAssignment a = new QManagementAssignment("a");

	@Override
	public <T extends AbstractResource> List<T> findChildren(String id,
			Class<T> clazz)
	{
		return (List<T>) QueryFactory.from(r).innerJoin(r.children, c)
				.where(r.id.eq(id).and(c.instanceOf(clazz))).list(c);
	}

	@Override
	public <T extends AbstractResource> List<T> findByManager(String managerId,
			Class<T> clazz)
	{
		return (List<T>) QueryFactory
				.from(a)
				.where(a.account.id.eq(managerId).and(
						a.resource.instanceOf(clazz))).list(a.resource);
	}
}