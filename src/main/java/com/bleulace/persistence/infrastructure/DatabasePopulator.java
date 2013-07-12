package com.bleulace.persistence.infrastructure;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * An abstract database populator, to be used to populate the database while
 * running in dev or test modes.
 * 
 * Concrete subclasses will check if they should populate the database with
 * dummy values whenever the context is refreshed.
 * 
 * @author Arleigh Dickerson
 * 
 */
abstract class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Override
	public final void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (shouldPopulate())
		{
			populate();
		}
	}

	/**
	 * 
	 * @return whether to execute database population
	 */
	protected abstract boolean shouldPopulate();

	/**
	 * executes database population
	 */
	protected abstract void populate();
}
