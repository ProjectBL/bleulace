package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.axonframework.domain.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.domain.management.event.TaskMarkedEvent;
import com.bleulace.domain.resource.model.AbstractChildResource;

@Entity
@RooToString
@RooJavaBean
public class Task extends AbstractChildResource implements ManageableResource
{
	@Column(nullable = false)
	private boolean complete = false;

	public void on(TaskMarkedEvent event, MetaData metaData)
	{
		complete = event.isComplete();
	}
}