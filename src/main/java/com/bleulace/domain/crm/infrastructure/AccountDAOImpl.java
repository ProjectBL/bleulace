package com.bleulace.domain.crm.infrastructure;

import java.util.List;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.QAccount;
import com.bleulace.domain.crm.model.QContactInformation;
import com.bleulace.domain.crm.model.QFriendRequest;
import com.bleulace.jpa.config.QueryFactory;
import com.mysema.query.jpa.impl.JPAQuery;

class AccountDAOImpl implements AccountDAOCustom
{
	private final QAccount a = new QAccount("a");
	private final QContactInformation i = a.contactInformation;
	private final QAccount f = new QAccount("f");
	private final QFriendRequest r = new QFriendRequest("r");

	@Override
	public List<String> findFriendIds(String id)
	{
		return makeQuery(id).innerJoin(a.friends, f).list(f.id);
	}

	@Override
	public List<Account> findFriends(String id)
	{
		return makeQuery(id).innerJoin(a.friends, f).list(f);
	}

	@Override
	public List<Account> findFriendRequests(String id)
	{
		return makeQuery(id).innerJoin(a.friendRequests, r).distinct()
				.list(r.requestor);
	}

	private JPAQuery makeQuery(String id)
	{
		Assert.notNull(id);
		return QueryFactory.from(a).where(a.id.eq(id));
	}

	@Override
	public List<Account> findBySearch(String searchTerm)
	{
		return QueryFactory
				.from(a)
				.where(i.firstName.containsIgnoreCase(searchTerm).or(
						i.lastName.containsIgnoreCase(searchTerm).or(
								a.username.containsIgnoreCase(searchTerm))))
				.list(a);
	}
}
