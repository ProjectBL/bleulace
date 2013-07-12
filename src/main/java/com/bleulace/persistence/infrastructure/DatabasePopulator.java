package com.bleulace.persistence.infrastructure;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

abstract class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (shouldPopulate())
		{
			populate();
		}
	}

	protected abstract boolean shouldPopulate();

	protected abstract void populate();
}
