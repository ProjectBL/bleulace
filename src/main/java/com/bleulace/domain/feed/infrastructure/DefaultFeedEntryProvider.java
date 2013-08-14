package com.bleulace.domain.feed.infrastructure;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.axonframework.domain.MetaData;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.cqrs.DomainEventPayload;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.EntityManagerReference;

public class DefaultFeedEntryProvider<T extends DomainEventPayload> implements
		FeedEntryProvider<T>
{
	@Autowired
	private AccountDAO dao;

	private final Class<T> clazz;

	private final boolean postToFriends;

	public DefaultFeedEntryProvider(Class<T> clazz, boolean postToFriends)
	{
		this.clazz = clazz;
		this.postToFriends = postToFriends;
	}

	@Override
	public Class<T> getEventClass()
	{
		return clazz;
	}

	@Override
	public Set<Account> provideAccounts(T event, MetaData metaData)
	{
		Set<Account> accounts = new HashSet<Account>();
		Account creator = EntityManagerReference.load(Account.class,
				metaData.getSubjectId());
		accounts.add(creator);
		if (postToFriends)
		{
			accounts.addAll(creator.getFriends());
		}
		return accounts;
	}

	@Override
	public boolean isInterested(T event, MetaData metaData)
	{
		return metaData.getSubjectId() != null;
	}

	@Override
	public Serializable[] provideData(T event, MetaData metaData)
	{
		return new Serializable[] {};
	}
}
