package com.bleulace.mgt.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.application.command.AddBundleCommand;
import com.bleulace.mgt.application.command.AddCommentCommand;
import com.bleulace.mgt.application.command.AssignManagerCommand;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.domain.event.BundleAddedEvent;
import com.bleulace.mgt.domain.event.CommentAddedEvent;
import com.bleulace.mgt.domain.event.ManagerAssignedEvent;
import com.bleulace.mgt.domain.event.ProjectCreatedEvent;
import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;

@Entity
@RooJavaBean
@Inheritance(strategy = InheritanceType.JOINED)
public class Project extends MgtResource implements
		EventSourcedAggregateRootMixin, Assignable.Mixin<ManagementAssignment>
{
	private static final long serialVersionUID = -1998536878318608268L;

	@EventSourcedMember
	@CascadeOnDelete
	@MapKeyColumn
	@OneToMany(cascade = CascadeType.ALL)
	private Map<String, Bundle> bundles = new HashMap<String, Bundle>();

	Project()
	{
	}

	Project(String id)
	{
		super(id);
	}

	public Project(CreateProjectCommand command)
	{
		super(command.getId());
		apply(command, ProjectCreatedEvent.class);
	}

	@Override
	public void on(ProjectCreatedEvent event)
	{
		map(event);

		if (event.getCreatorId() != null)
		{
			ManagerAssignedEvent toApply = new ManagerAssignedEvent();
			toApply.setAccountId(event.getCreatorId());
			toApply.setRole(ManagementAssignment.OWN);
			apply(toApply);
		}
	}

	public void handle(AssignManagerCommand command)
	{
		apply(command, ManagerAssignedEvent.class);
	}

	public void handle(AddBundleCommand command)
	{
		apply(command, BundleAddedEvent.class);
	}

	public void on(BundleAddedEvent event)
	{
		Bundle bundle = new Bundle(this, event);
		bundles.put(bundle.getId(), bundle);
	}

	public void handle(AddTaskCommand command)
	{
		apply(command, TaskAddedEvent.class);
	}

	public void handle(AddCommentCommand command)
	{
		apply(command, CommentAddedEvent.class);
	}
}