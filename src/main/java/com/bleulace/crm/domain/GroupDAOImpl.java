package com.bleulace.crm.domain;

import java.util.List;

import org.springframework.util.Assert;

import com.bleulace.mgt.domain.QAccountGroup;
import com.bleulace.persistence.infrastructure.QueryFactory;

class GroupDAOImpl implements GroupDAOCustom
{
	@Override
	public AccountGroup findOneByTitle(String title)
	{
		Assert.notNull(title);
		QAccountGroup g = QAccountGroup.accountGroup;
		return QueryFactory.from(g).where(g.title.equalsIgnoreCase(title))
				.singleResult(g);
	}

	@Override
	public List<AccountGroup> findByTitle(String title)
	{
		Assert.notNull(title);
		QAccountGroup g = QAccountGroup.accountGroup;
		return QueryFactory.from(g).where(g.title.containsIgnoreCase(title))
				.list(g);
	}
}
