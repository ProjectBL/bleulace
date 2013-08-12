package com.bleulace.domain.feed.infrastructure;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.axonframework.domain.MetaData;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.cqrs.DomainEventPayload;
import com.bleulace.domain.crm.infrastructure.AccountDAO;

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
	public Set<String> provideAccountIds(T event, MetaData metaData)
	{
		Set<String> ids = new HashSet<String>();
		String creatorId = (String) metaData.get("subjectId");
		if (creatorId != null)
		{
			ids.add(creatorId);
			if (postToFriends)
			{
				ids.addAll(dao.findFriendIds(creatorId));
			}
		}
		return ids;
	}

	@Override
	public boolean isInterested(T event, MetaData metaData)
	{
		return metaData.getSubjectId() != null;
	}

	@Override
	public Serializable[] provideMetaData(T event, MetaData metaData)
	{
		return new Serializable[] {};
	}
}
