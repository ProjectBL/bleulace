package com.bleulace.domain.feed.event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.domain.MetaData;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.unitofwork.DefaultUnitOfWorkFactory;
import org.axonframework.unitofwork.SaveAggregateCallback;
import org.axonframework.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.DomainEventPayload;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.feed.infrastructure.DefaultFeedEntryProvider;
import com.bleulace.domain.feed.infrastructure.FeedEntryFactoryLocater;
import com.bleulace.domain.feed.infrastructure.FeedEntryProvider;
import com.bleulace.domain.feed.model.FeedEntry;

@Component
class FeedUpdatingListener implements SaveAggregateCallback<Account>
{
	@Autowired
	private FeedEntryFactoryLocater locater;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private DefaultUnitOfWorkFactory uowFactory;

	@PersistenceContext
	private EntityManager em;

	@EventHandler
	public void handle(DomainEventPayload event, MetaData metaData)
	{
		final FeedEntryProvider<DomainEventPayload> provider = getProvider(event
				.getClass());

		if (provider.isInterested(event, metaData))
		{
			final UnitOfWork uow = uowFactory.createUnitOfWork();
			try
			{
				final FeedEntry entry = new FeedEntry(event,
						provider.provideMetaData(event, metaData));
				for (String id : provider.provideAccountIds(event, metaData))
				{
					doFeedUpdate(id, entry, uow);
				}
				uow.commit();
			}
			catch (Exception e)
			{
				uow.rollback(e);
			}
		}
	}

	@Override
	public void save(Account aggregate)
	{
		if (aggregate.isNew())
		{
			em.persist(aggregate);
		}
		else
		{
			em.merge(aggregate);
		}
	}

	private void doFeedUpdate(String id, FeedEntry entry, UnitOfWork uow)
	{
		Account account = em.getReference(Account.class, id);
		uow.registerAggregate(account, eventBus, this);
		account.getFeedEntries().add(entry);
	}

	@SuppressWarnings("unchecked")
	private FeedEntryProvider<DomainEventPayload> getProvider(
			Class<?> eventClass)
	{
		FeedEntryProvider<DomainEventPayload> factory = (FeedEntryProvider<DomainEventPayload>) locater
				.locate(eventClass);

		if (factory == null)
		{
			factory = getDefaultProvider(eventClass);
		}

		return factory;
	}

	@SuppressWarnings("unchecked")
	private FeedEntryProvider<DomainEventPayload> getDefaultProvider(
			Class<?> eventClass)
	{
		return new DefaultFeedEntryProvider<DomainEventPayload>(
				(Class<DomainEventPayload>) eventClass, true);
	}
}