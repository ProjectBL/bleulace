package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import org.axonframework.eventsourcing.EventSourcedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.feed.NewsFeedEnvelope;
import com.bleulace.mgt.application.command.AddBundleCommand;
import com.bleulace.mgt.application.command.AddCommentCommand;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.AssignManagerCommand;
import com.bleulace.mgt.application.command.AssignTaskCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.domain.event.BundleAddedEvent;
import com.bleulace.mgt.domain.event.CommentAddedEvent;
import com.bleulace.mgt.domain.event.ManagerAssignedEvent;
import com.bleulace.mgt.domain.event.ProjectCreatedEvent;
import com.bleulace.mgt.domain.event.ResourceCompletedEvent;
import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.mgt.domain.event.TaskAssignedEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.persistence.infrastructure.QueryFactory;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean
@Inheritance(strategy = InheritanceType.JOINED)
public class Project extends Resource implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = -1998536878318608268L;

	@EventSourcedMember
	@CascadeOnDelete
	@MapKeyColumn
	@OneToMany(cascade = CascadeType.ALL)
	private Map<String, Bundle> bundles = new HashMap<String, Bundle>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<JPAManagementPermission> assignees = new ArrayList<JPAManagementPermission>();

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
			new NewsFeedEnvelope().addFriends(event.getCreatorId())
					.withPayloads(this, event).send();
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

	public void handle(AssignTaskCommand command)
	{
		apply(command, TaskAssignedEvent.class);
	}

	public void on(ManagerAssignedEvent event)
	{
		if (this.getId().equals(event.getId()))
		{
			EntityManager em = EntityManagerReference.get();
			Account account = em.getReference(Account.class,
					event.getAccountId());
			JPAManagementPermission permission = new JPAManagementPermission(
					account, this, event.getRole());
			if (event.getRole() == null)
			{
				this.assignees.remove(permission);
			}
			else
			{
				assignees.add(new JPAManagementPermission(account, this, event
						.getRole()));

				new NewsFeedEnvelope().addFriends(event.getAccountId())
						.withPayloads(this, event).send();
			}

			for (String id : getChildIds())
			{
				apply(new ManagerAssignedEvent(id, event.getAccountId(), null));
			}
		}
	}

	public void on(ResourceCompletedEvent event)
	{
		if (event.getId().equals(getId()))
		{
			for (EventSourcedEntity child : getChildEntities())
			{
				if (child instanceof Task)
				{
					((Task) child).setComplete(true);
				}
			}
			new NewsFeedEnvelope().addAccounts(getManagers())
					.withPayloads(this, event).send();
		}
	}

	public Double getPercentComplete()
	{
		int completed = 0;
		int total = 0;
		for (EventSourcedEntity child : getChildEntities())
		{
			if (child instanceof Task)
			{
				total++;
				if (((Task) child).isComplete())
				{
					completed++;
				}
			}
		}
		return new Double(completed) / new Double(total);
	}

	@Override
	protected Set<String> getParentIds()
	{
		return new HashSet<String>();
	}

	@Override
	protected Set<String> getChildIds()
	{
		Set<String> ids = new HashSet<String>();
		for (EventSourcedEntity entity : getChildEntities())
		{
			Resource resource = (Resource) entity;
			ids.add(resource.getId());
		}
		return ids;
	}

	@Override
	public boolean isComplete()
	{
		return getPercentComplete().equals(new Double(1));
	}

	protected List<Account> getManagers()
	{
		QJPAManagementPermission p = QJPAManagementPermission.jPAManagementPermission;
		return QueryFactory.from(p).where(p.project.eq(this)).list(p.account);
	}
}