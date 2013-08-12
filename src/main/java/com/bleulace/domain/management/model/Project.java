package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import org.axonframework.domain.MetaData;

import com.bleulace.cqrs.MappingAspect;
import com.bleulace.domain.crm.model.CommentableRoot;
import com.bleulace.domain.management.command.AssignManagersCommand;
import com.bleulace.domain.management.command.CreateBundleCommand;
import com.bleulace.domain.management.command.CreateManageableResourceCommand;
import com.bleulace.domain.management.command.CreateProjectCommand;
import com.bleulace.domain.management.command.CreateTaskCommand;
import com.bleulace.domain.management.command.MarkTaskCommand;
import com.bleulace.domain.management.event.BundleCreatedEvent;
import com.bleulace.domain.management.event.ManageableResourceCreatedEvent;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.domain.management.event.ProjectCreatedEvent;
import com.bleulace.domain.management.event.TaskCreatedEvent;
import com.bleulace.domain.management.event.TaskMarkedEvent;
import com.bleulace.domain.resource.model.AbstractRootResource;

@Entity
public class Project extends AbstractRootResource implements
		ManageableResource, CommentableRoot
{
	Project()
	{
	}

	public Project(CreateProjectCommand command, MetaData metaData)
	{
		ProjectCreatedEvent event = new ProjectCreatedEvent();
		initializeCreatedEvent(command, metaData.getSubjectId(), event);
		apply(event, metaData);
	}

	public void handle(AssignManagersCommand command, MetaData metaData)
	{
		for (String accountId : command.getAccountIds())
		{
			ManagerAssignedEvent event = MappingAspect.map(this,
					ManagerAssignedEvent.class);
			MappingAspect.map(command, event);
			event.setAssigneeId(accountId);
			apply(event, metaData);
		}
	}

	public void handle(CreateBundleCommand command, MetaData metaData)
	{
		BundleCreatedEvent event = new BundleCreatedEvent();
		initializeCreatedEvent(command, metaData.getSubjectId(), event);
		apply(event, metaData);
	}

	public void on(BundleCreatedEvent event)
	{
		addChild(MappingAspect.map(event, Bundle.class));
	}

	public void handle(CreateTaskCommand command, MetaData metaData)
	{
		TaskCreatedEvent event = new TaskCreatedEvent();
		initializeCreatedEvent(command, metaData.getSubjectId(), event);
		apply(event, metaData);
	}

	public void on(TaskCreatedEvent event)
	{
		Task t = new Task();
		MappingAspect.map(event, t);
		addChild(t);
	}

	public void handle(MarkTaskCommand command, MetaData metaData)
	{
		apply(MappingAspect.map(command, TaskMarkedEvent.class), metaData);
	}

	private void initializeCreatedEvent(
			CreateManageableResourceCommand command, String subjectId,
			ManageableResourceCreatedEvent event)
	{
		MappingAspect.map(command, event);
		MappingAspect.map(this, event);
	}
}