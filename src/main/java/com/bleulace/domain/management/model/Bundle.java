package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import org.axonframework.domain.MetaData;

import com.bleulace.domain.management.event.TaskCreatedEvent;
import com.bleulace.domain.management.event.TaskMarkedEvent;
import com.bleulace.domain.resource.model.AbstractChildResource;
import com.bleulace.utils.dto.Mapper;

@Entity
public class Bundle extends AbstractChildResource implements ManageableResource
{
	public void on(TaskCreatedEvent event, MetaData metaData)
	{
		Task t = new Task();
		Mapper.map(event, t);
		addChild(t);
	}

	public void on(TaskMarkedEvent event, MetaData metaData)
	{
	}
}