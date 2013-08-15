package com.bleulace.domain.resource.infrastructure;

import static java.lang.String.format;

import javax.persistence.EntityNotFoundException;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.HybridJpaRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.ConflictingAggregateVersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bleulace.cqrs.event.EventBusAware;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.domain.resource.model.AbstractRootResource;
import com.bleulace.jpa.EntityManagerReference;

@Repository
class ResourceRepository extends HybridJpaRepository<AbstractRootResource>
		implements EventBusAware
{
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
		AbstractResource resource;
		try
		{
			resource = EntityManagerReference.load(AbstractResource.class,
					aggregateIdentifier);
		}
		catch (EntityNotFoundException e)
		{
			throw new AggregateNotFoundException(aggregateIdentifier, format(
					"Aggregate [%s] with identifier [%s] not found",
					getAggregateType().getSimpleName(), aggregateIdentifier));
		}

		AggregateRoot<?> aggregate = resource.getRoot();
		if (expectedVersion != null && aggregate.getVersion() != null
				&& !expectedVersion.equals(aggregate.getVersion()))
		{
			throw new ConflictingAggregateVersionException(aggregateIdentifier,
					expectedVersion, aggregate.getVersion());
		}

		return resource.getRoot();
	}
}