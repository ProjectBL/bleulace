package com.bleulace.domain.feed.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FeedEntryProviderLocaterImpl implements
		FeedEntryProviderLocater, ApplicationContextAware
{
	private final Map<Class, FeedEntryProvider> map = new HashMap<Class, FeedEntryProvider>();

	@Override
	public FeedEntryProvider locate(Class clazz)
	{
		return map.get(clazz);
	}

	private void add(FeedEntryProvider factory)
	{
		map.put(factory.getEventClass(), factory);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException
	{
		for (FeedEntryProvider<?> factory : applicationContext.getBeansOfType(
				FeedEntryProvider.class).values())
		{
			add(factory);
		}
	}
}