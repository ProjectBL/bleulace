package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.application.command.AddManagerCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.domain.event.ManagerAddedEvent;
import com.bleulace.mgt.domain.event.ProjectCreatedEvent;
import com.bleulace.persistence.utils.EntityManagerReference;

@Entity
@RooJavaBean
@Inheritance(strategy = InheritanceType.JOINED)
public class Project extends AbstractAnnotatedAggregateRoot<String> implements
		TaskableMixin, Manageable
{
	private static final long serialVersionUID = -1998536878318608268L;

	@Id
	private String id = UUID.randomUUID().toString();

	@Column(nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ProjectManager> mgt = new ArrayList<ProjectManager>();

	Project()
	{
	}

	@CommandHandler
	Project(CreateProjectCommand command)
	{
		apply(new ModelMapper().map(command, ProjectCreatedEvent.class));
	}

	@EventHandler
	public void on(ProjectCreatedEvent event)
	{
		name = event.getName();
		apply(new ManagerAddedEvent(event.getCreatorId(), ManagementLevel.OWN));
	}

	@CommandHandler
	public void handle(AddManagerCommand command)
	{
		apply(new ModelMapper().map(command, ManagerAddedEvent.class));
	}

	@EventHandler
	public void on(ManagerAddedEvent event)
	{
		EntityManager entityManager = EntityManagerReference.get();
		Account account = entityManager.getReference(Account.class,
				event.getAccountId());
		account.getPermissions().add(
				new ManagementPermission(getId(), event.getLevel()));
		entityManager.merge(account);
	}

	@Override
	public Map<Account, ManagementLevel> getManagers()
	{
		return new ProjectManagerMap(this);
	}

	protected List<ProjectManager> getMgt()
	{
		return mgt;
	}
}