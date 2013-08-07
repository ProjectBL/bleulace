package com.bleulace.mgt.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.HybridJpaRepository;
import org.axonframework.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectRepository extends HybridJpaRepository<Project>
{
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private BundleDAO bundleDAO;

	@Autowired
	public ProjectRepository(EntityManagerProvider entityManagerProvider,
			EventStore eventStore, EventBus eventBus)
	{
		super(entityManagerProvider, Project.class);
		setEventBus(eventBus);
		setEventStore(eventStore);
	}

	@Override
	protected Project doLoad(Object aggregateIdentifier, Long expectedVersion)
	{
		aggregateIdentifier = getProjectIdentifier((String) aggregateIdentifier);
		return super.doLoad(aggregateIdentifier, expectedVersion);
	}

	public String getProjectIdentifier(String id)
	{
		Resource resource = em.getReference(Resource.class, id);

		if (resource.getClass().equals(Project.class))
		{
			return id;
		}

		if (resource instanceof Task)
		{
			resource = bundleDAO.findByTaskId(((Task) resource).getId());
		}

		if (resource instanceof Bundle)
		{
			return ((Bundle) resource).getProject().getId();
		}

		if (resource instanceof Event)
		{
			return ((Event) resource).getId();
		}

		throw new IllegalArgumentException();
	}

	@Override
	protected void doDeleteWithLock(Project aggregate)
	{
		super.doDeleteWithLock(aggregate);
	}

	@Override
	protected void doSaveWithLock(Project aggregate)
	{
		super.doSaveWithLock(aggregate);
	}
}
