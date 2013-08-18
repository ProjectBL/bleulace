package com.bleulace.jpa.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bleulace.utils.SystemProfiles;

@Component
@Profile(SystemProfiles.DEV)
class DatabasePopulator implements ApplicationListener<ContextRefreshedEvent>
{
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (shouldPopulate())
		{
			populate();
		}
	}

	private boolean shouldPopulate()
	{
		return false;
	}

	private void populate()
	{

	}
}