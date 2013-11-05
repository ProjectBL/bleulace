package com.bleulace.domain.resource.infrastructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.QManager;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.domain.resource.model.QAbstractResource;
import com.bleulace.jpa.config.QueryFactory;

class ResourceDAOImpl implements ResourceDAOCustom
{
	private final QAbstractResource r = new QAbstractResource("r");
	private final QManager a = new QManager("a");

	@Autowired
	private EventDAO eventDAO;

	@Override
	public List<String> findIdsForManager(String managerId)
	{
		return QueryFactory.from(r).innerJoin(r.managers, a)
				.where(a.account.id.eq(managerId)).list(r.id);
	}

	@Override
	public List<Account> findManagers(AbstractResource resource)
	{
		return QueryFactory.from(r).innerJoin(r.managers, a).distinct()
				.where(r.eq(resource)).list(a.account);

	}
}