package com.bleulace.domain.feed.infrastructure;

public interface FeedEntryFactoryLocater
{
	public FeedEntryProvider<?> locate(Class<?> clazz);
}
