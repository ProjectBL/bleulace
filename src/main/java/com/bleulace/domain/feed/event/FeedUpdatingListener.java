package com.bleulace.domain.feed.event;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.domain.MetaData;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.unitofwork.DefaultUnitOfWorkFactory;
import org.axonframework.unitofwork.SaveAggregateCallback;
import org.axonframework.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.DomainEventPayload;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.feed.infrastructure.DefaultFeedEntryProvider;
import com.bleulace.domain.feed.infrastructure.FeedEntryProvider;
import com.bleulace.domain.feed.infrastructure.FeedEntryProviderLocater;
import com.bleulace.domain.feed.model.FeedEntry;
import com.bleulace.utils.SystemProfiles;

@Component
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class FeedUpdatingListener implements SaveAggregateCallback<Account>,
		Serializable
{
	@Autowired
	private transient FeedEntryProviderLocater locater;

	@Autowired
	private transient EventBus eventBus;

	@Autowired
	private transient DefaultUnitOfWorkFactory uowFactory;

	@PersistenceContext
	private transient EntityManager em;

	@EventHandler
	@Transactional
	public void handle(DomainEventPayload event, MetaData metaData)
	{
		final FeedEntryProvider<DomainEventPayload> provider = getProvider(event
				.getClass());

		if (provider.isInterested(event, metaData))
		{
			final UnitOfWork uow = uowFactory.createUnitOfWork();
			try
			{
				final FeedEntry entry = new FeedEntry(event, metaData,
						provider.provideData(event, metaData));
				Set<Account> accounts = provider.provideAccounts(event,
						metaData);
				for (Account account : accounts)
				{
					uow.registerAggregate(account, eventBus, this);
					account.getFeedEntries().add(entry.clone());
				}

				uow.commit();
				em.flush();

				for (Account account : accounts)
				{
					eventBus.publish(GenericEventMessage
							.asEventMessage(new FeedUpdatedEvent(account
									.getId())));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
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