package com.bleulace.domain.resource.model;

import static java.lang.String.format;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.HybridJpaRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.ConflictingAggregateVersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bleulace.cqrs.event.EventBusAware;

@Repository
class ResourceRepository extends HybridJpaRepository<AbstractRootResource>
		implements EventBusAware
{
	@PersistenceContext
	private EntityManager em;

	@Autowired
	ResourceRepository(EntityManagerProvider entityManagerProvider,
			EventStore eventStore)
	{
		super(entityManagerProvider, AbstractRootResource.class);
		setEventBus(eventBus());
		setEventStore(eventStore);
		setForceFlushOnSave(true);
	}

	@Override
	protected AbstractRootResource doLoad(Object aggregateIdentifier,
			Long expectedVersion)
	{
		Resource resource = null;
		try
		{
			resource = em.getReference(AbstractResource.class,
					aggregateIdentifier);
		}
		catch (EntityNotFoundException e)
		{
			throw new AggregateNotFoundException(aggregateIdentifier, format(
					"Aggregate [%s] with identifier [%s] not found",
					getAggregateType().getSimpleName(), aggregateIdentifier));
		}

		AggregateRoot<?> aggregate = (AggregateRoot<?>) resource.getRoot();
		if (expectedVersion != null && aggregate.getVersion() != null
				&& !expectedVersion.equals(aggregate.getVersion()))
		{
			throw new ConflictingAggregateVersionException(aggregateIdentifier,
					expectedVersion, aggregate.getVersion());
		}

		return (AbstractRootResource) resource.getRoot();
	}
}