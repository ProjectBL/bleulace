package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.application.command.AddBundleCommand;
import com.bleulace.mgt.application.command.AddManagerCommand;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.domain.event.ManagerAddedEvent;
import com.bleulace.mgt.domain.event.ProjectCreatedEvent;
import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.utils.EntityManagerReference;

@Entity
@RooJavaBean
@Inheritance(strategy = InheritanceType.JOINED)
public class Project implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = -1998536878318608268L;

	@JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Project parent;

	@Column(nullable = false)
	private String title;

	@OneToMany(cascade = CascadeType.ALL)
	@MapKey(name = "account")
	private Map<Account, ProjectManager> managers = new HashMap<Account, ProjectManager>();

	@EventSourcedMember
	@OneToMany(cascade = CascadeType.ALL)
	@MapKey
	private Map<String, Task> tasks = new HashMap<String, Task>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Project> bundles = new ArrayList<Project>();

	Project()
	{
	}

	public Project(CreateProjectCommand command)
	{
		setId(command.getId());
		apply(command, ProjectCreatedEvent.class);
	}

	public Project(AddBundleCommand command)
	{
		this((CreateProjectCommand) command);
	}

	public void on(ProjectCreatedEvent event)
	{
		map(event);

		if (event.getCreatorId() != null)
		{
			apply(new ManagerAddedEvent(event.getCreatorId(),
					ManagementLevel.OWN));
		}

		if (event.getParentId() != null)
		{
			parent = EntityManagerReference.get().getReference(Project.class,
					event.getParentId());
			parent.getBundles().add(this);
		}
	}

	public void handle(AddManagerCommand command)
	{
		apply(command, ManagerAddedEvent.class);
	}

	public void on(ManagerAddedEvent event)
	{
		String accountId = event.getAccountId();
		EntityManager entityManager = EntityManagerReference.get();
		Account account = entityManager.getReference(Account.class, accountId);
		// account.getPermissions().add(
		// new ManagementPermission(getId(), event.getLevel()));
		entityManager.merge(account);
		getManagers().put(account,
				new ProjectManager(this, account, event.getLevel()));
	}

	public void handle(AddTaskCommand command)
	{
		apply(command, TaskAddedEvent.class);
	}

	public void on(TaskAddedEvent event)
	{
		Task task = new Task(this, event.getTitle());
		tasks.put(task.getId(), task);
	}
}