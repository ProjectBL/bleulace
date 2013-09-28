package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.QManagementAssignment;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.domain.resource.model.QAbstractResource;
import com.bleulace.jpa.config.QueryFactory;

@SuppressWarnings("unchecked")
class ResourceDAOImpl implements ResourceDAOCustom
{
	@Override
	public <T extends AbstractResource> List<T> findChildren(String id,
			Class<T> clazz)
	{
		QAbstractResource r = new QAbstractResource("r");
		QAbstractResource c = new QAbstractResource("c");
		return (List<T>) QueryFactory.from(r).innerJoin(r.children, c)
				.where(r.id.eq(id).and(c.instanceOf(clazz))).list(c);
	}

	@Override
	public <T extends AbstractResource> List<T> findByManager(String managerId,
			Class<T> clazz)
	{
		QManagementAssignment a = new QManagementAssignment("a");
		return (List<T>) QueryFactory
				.from(a)
				.where(a.account.id.eq(managerId).and(
						a.parent.instanceOf(clazz))).list(a.parent);
	}

	@Override
	public <T extends AbstractResource> List<T> findByManager(String managerId,
			ManagementLevel level, Class<T> clazz)
	{
		QManagementAssignment a = new QManagementAssignment("a");
		return (List<T>) QueryFactory
				.from(a)
				.where(a.account.id.eq(managerId)
						.and(a.parent.instanceOf(clazz)).and(a.role.eq(level)))
				.list(a.parent);
	}
}