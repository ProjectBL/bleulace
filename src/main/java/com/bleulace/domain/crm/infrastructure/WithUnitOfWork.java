package com.bleulace.domain.crm.infrastructure;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.unitofwork.CurrentUnitOfWork;
import org.axonframework.unitofwork.SaveAggregateCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.cqrs.event.EventBusAware;
import com.bleulace.domain.resource.model.AbstractRootResource;

@Configurable
public class WithUnitOfWork implements EventBusAware,
		SaveAggregateCallback<AbstractRootResource>
{
	private static final WithUnitOfWork INSTANCE = new WithUnitOfWork();
	@Autowired
	private EntityManagerProvider provider;

	@Autowired
	private EventBus eventBus;

	private WithUnitOfWork()
	{
	}

	public static void register(AbstractRootResource resource)
	{
		CurrentUnitOfWork.get().registerAggregate(resource, INSTANCE.eventBus,
				INSTANCE);
	}

	@Override
	public void save(AbstractRootResource aggregate)
	{
		provider.getEntityManager().merge(aggregate);
	}
}