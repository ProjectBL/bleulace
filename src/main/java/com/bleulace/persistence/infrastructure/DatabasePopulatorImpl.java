package com.bleulace.persistence.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({ "dev", "prod" })
public class DatabasePopulatorImpl extends DatabasePopulator
{
	@Override
	protected boolean shouldPopulate()
	{
		return false;
	}

	@Override
	protected void populate()
	{
	}
}