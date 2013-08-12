package com.bleulace.domain.feed.infrastructure;

import java.io.Serializable;
import java.util.Set;

import org.axonframework.domain.MetaData;

public interface FeedEntryProvider<T>
{
	public Class<T> getEventClass();

	public Serializable[] provideMetaData(T event, MetaData metaData);

	public Set<String> provideAccountIds(T event, MetaData metaData);

	public boolean isInterested(T event, MetaData metaData);
}