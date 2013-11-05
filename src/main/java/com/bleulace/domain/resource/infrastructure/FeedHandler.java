package com.bleulace.domain.resource.infrastructure;

import java.util.Set;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.resource.model.AbstractResource;

public interface FeedHandler
{
	public void resourceCreated(AbstractResource entity);

	public void resourceUpdated(AbstractResource entity);

	public void resourceDeleted(AbstractResource entity);

	public Set<Account> findInterested(AbstractResource entity);

	public Account getCurrent();
}