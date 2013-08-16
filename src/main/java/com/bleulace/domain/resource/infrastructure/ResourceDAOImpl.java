package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import com.bleulace.domain.resource.model.AbstractChildResource;
import com.bleulace.domain.resource.model.QAbstractChildResource;
import com.bleulace.domain.resource.model.QAbstractResource;
import com.bleulace.jpa.config.QueryFactory;

class ResourceDAOImpl implements ResourceDAOCustom
{
	@Override
	public <T extends AbstractChildResource> List<T> findChildren(String id,
			Class<T> clazz)
	{
		QAbstractResource r = new QAbstractResource("r");
		QAbstractChildResource c = new QAbstractChildResource("c");
		return (List<T>) QueryFactory.from(r).innerJoin(r.children, c)
				.where(r.id.eq(id).and(c.instanceOf(clazz))).list(c);
	}
}