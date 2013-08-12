package com.bleulace.domain.feed.presentation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
class FeedDTOFactoryLocaterImpl implements FeedDTOFactoryLocater,
		ApplicationContextAware
{
	private final Map<Class<?>, FeedDTOFactory> map = new HashMap<Class<?>, FeedDTOFactory>();

	@Override
	public FeedDTOFactory locate(Class<?> clazz)
	{
		return map.get(clazz);
	}

	private void add(FeedDTOFactory factory)
	{
		map.put(factory.getEventClass(), factory);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException
	{
		for (FeedDTOFactory factory : applicationContext.getBeansOfType(
				FeedDTOFactory.class).values())
		{
			add(factory);
		}
	}
}
