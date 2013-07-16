package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.application.command.AddManagerCommand;
import com.bleulace.mgt.application.command.CreateBundleCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.domain.event.BundleCreatedEvent;
import com.bleulace.mgt.domain.event.ManagerAddedEvent;
import com.bleulace.mgt.domain.event.ProjectCreatedEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.utils.EntityManagerReference;

@Entity
@RooJavaBean
@Inheritance(strategy = InheritanceType.JOINED)
public class Project implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = -1998536878318608268L;

	@Column(nullable = false)
	private String title;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ProjectManager> mgrList = new ArrayList<ProjectManager>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Bundle> bundles = new ArrayList<Bundle>();

	Project()
	{
	}

	public Map<Account, ManagementLevel> getManagers()
	{
		return new ProjectManagerMap(this);
	}

	public Project(CreateProjectCommand command)
	{
		setId(command.getId());
		apply(new ModelMapper().map(command, ProjectCreatedEvent.class));
	}

	public void on(ProjectCreatedEvent event)
	{
		title = event.getTitle();
		apply(new ManagerAddedEvent(event.getCreatorId(), ManagementLevel.OWN));
	}

	public void handle(AddManagerCommand command)
	{
		apply(new ModelMapper().map(command, ManagerAddedEvent.class));
	}

	public void on(ManagerAddedEvent event)
	{
		EntityManager entityManager = EntityManagerReference.get();
		Account account = entityManager.getReference(Account.class,
				event.getAccountId());
		account.getPermissions().add(
				new ManagementPermission(getId(), event.getLevel()));
		entityManager.merge(account);
	}

	public void handle(CreateBundleCommand command)
	{
		apply(new ModelMapper().map(command, BundleCreatedEvent.class));
	}

	public void on(BundleCreatedEvent event)
	{
		Bundle bundle = new Bundle(this);
		new ModelMapper().map(event, bundle);
		bundles.add(bundle);
	}
}