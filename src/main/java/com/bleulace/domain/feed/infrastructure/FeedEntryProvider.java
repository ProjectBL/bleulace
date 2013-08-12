package com.bleulace.domain.feed.infrastructure;

import java.io.Serializable;
import java.util.Set;

import org.axonframework.domain.MetaData;

import com.bleulace.domain.crm.model.Account;

public interface FeedEntryProvider<T>
{
	public Class<T> getEventClass();

	public Serializable[] provideData(T event, MetaData metaData);

	public Set<Account> provideAccounts(T event, MetaData metaData);

	public boolean isInterested(T event, MetaData metaData);
}