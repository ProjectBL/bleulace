package com.bleulace.domain.crm.infrastructure;

import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.QAccount;
import com.bleulace.domain.crm.model.QContactInformation;
import com.bleulace.domain.crm.model.QFriendRequest;
import com.bleulace.domain.management.model.QManagementAssignment;
import com.bleulace.jpa.config.QueryFactory;
import com.mysema.query.jpa.impl.JPAQuery;

class AccountDAOImpl implements AccountDAOCustom
{
	private final QAccount a = new QAccount("a");
	private final QManagementAssignment ass = new QManagementAssignment("ass");
	private final QContactInformation i = a.contactInformation;
	private final QAccount f = new QAccount("f");
	private final QFriendRequest req = new QFriendRequest("rec");

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
		return makeQuery(id).innerJoin(a.friendRequests, req).distinct()
				.list(req.requestor);
	}

	@Override
	public List<Account> findRandomFriends(String accountId, int count)
	{
		Assert.isTrue(count > -1);
		List<Account> list = QueryFactory.from(a).innerJoin(a.friends, f)
				.where(f.id.eq(accountId)).distinct().limit(count).list(a);
		Collections.shuffle(list);
		return list;
	}

	@Override
	public List<Account> findPeopleYouMightKnow(String accountId, int count)
	{
		List<Account> list = QueryFactory.from(a).innerJoin(a.friends, f)
				.where(f.id.ne(accountId).and(a.id.ne(accountId))).distinct()
				.limit(count).list(a);
		Collections.shuffle(list);
		return list;
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

	private JPAQuery makeQuery(String id)
	{
		Assert.notNull(id);
		return QueryFactory.from(a).where(a.id.eq(id));
	}
}
