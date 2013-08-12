package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import org.axonframework.domain.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.event.TaskMarkedEvent;
import com.bleulace.domain.resource.model.AbstractChildResource;

@Entity
@RooJavaBean
public class Task extends AbstractChildResource implements ManageableResource
{
	private boolean complete = false;

	@Override
	public boolean isLeaf()
	{
		return true;
	}

	public void on(TaskMarkedEvent event, MetaData metaData)
	{
		complete = event.isComplete();
	}
}