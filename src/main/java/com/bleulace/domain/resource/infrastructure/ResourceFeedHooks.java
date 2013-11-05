package com.bleulace.domain.resource.infrastructure;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.resource.model.AbstractResource;

@Configurable
public class ResourceFeedHooks
{
	@Autowired
	private FeedHandler handler;

	@PostPersist
	public void postPersist(AbstractResource entity)
	{
		handler.resourceCreated(entity);
	}

	@PostUpdate
	public void postUpdate(AbstractResource entity)
	{
		handler.resourceUpdated(entity);
	}

	@PostRemove
	public void postRemove(AbstractResource entity)
	{
		handler.resourceDeleted(entity);
	}
}