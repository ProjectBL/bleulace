package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import org.axonframework.common.annotation.MetaData;

import com.bleulace.cqrs.ShiroMetaData;
import com.bleulace.domain.crm.model.CommentableRoot;
import com.bleulace.domain.management.command.AssignManagersCommand;
import com.bleulace.domain.management.command.CreateBundleCommand;
import com.bleulace.domain.management.command.CreateManageableResourceCommand;
import com.bleulace.domain.management.command.CreateProjectCommand;
import com.bleulace.domain.management.command.CreateTaskCommand;
import com.bleulace.domain.management.event.BundleCreatedEvent;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.domain.management.event.ManageableResourceCreatedEvent;
import com.bleulace.domain.management.event.ProjectCreatedEvent;
import com.bleulace.domain.management.event.TaskCreatedEvent;
import com.bleulace.domain.resource.model.AbstractRootResource;

@Entity
public class Project extends AbstractRootResource implements
		ManageableResource, CommentableRoot
{
	Project()
	{
	}

	public Project(CreateProjectCommand command,
			@MetaData(ShiroMetaData.SUBJECT_ID) String subjectId)
	{
		ProjectCreatedEvent event = new ProjectCreatedEvent();
		initializeCreatedEvent(command, subjectId, event);
		apply(event);
	}

	public void on(ProjectCreatedEvent event)
	{
		map(event);
	}

	public void handle(AssignManagersCommand command,
			@MetaData(ShiroMetaData.SUBJECT_ID) String subjectId)
	{
		for (String accountId : command.getAccountIds())
		{
			ManagerAssignedEvent event = mapper().map(this,
					ManagerAssignedEvent.class);
			mapper().map(command, event);
			event.setAssignerId(subjectId);
			event.setAssigneeId(accountId);
			apply(event);
		}
	}

	public void handle(CreateBundleCommand command,
			@MetaData(ShiroMetaData.SUBJECT_ID) String subjectId)
	{
		BundleCreatedEvent event = new BundleCreatedEvent();
		initializeCreatedEvent(command, subjectId, event);
		apply(event);
	}

	public void on(BundleCreatedEvent event)
	{
		addChild(mapper().map(event, Bundle.class));
	}

	public void handle(CreateTaskCommand command,
			@MetaData(ShiroMetaData.SUBJECT_ID) String subjectId)
	{
		System.out.println("foo");
		TaskCreatedEvent event = new TaskCreatedEvent();
		initializeCreatedEvent(command, subjectId, event);
		apply(event);
	}

	public void on(TaskCreatedEvent event)
	{
		System.out.println("bar");
		Task t = new Task();
		mapper().map(event, t);
		addChild(t);
	}

	private void initializeCreatedEvent(CreateManageableResourceCommand command,
			String subjectId, ManageableResourceCreatedEvent event)
	{
		mapper().map(command, event);
		mapper().map(this, event);
		event.setCreatorId(subjectId);
	}
}