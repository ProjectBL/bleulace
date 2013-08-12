package com.bleulace.domain.feed.infrastructure;

public interface FeedEntryProviderLocater
{
	public FeedEntryProvider<?> locate(Class<?> clazz);
}
