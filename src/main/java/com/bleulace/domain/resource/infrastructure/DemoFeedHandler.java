package com.bleulace.domain.resource.infrastructure;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.FeedEntry;
import com.bleulace.domain.crm.model.FeedEntryScenario;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.utils.ctx.SpringApplicationContext;

@Component
class DemoFeedHandler implements FeedHandler
{
	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private EventDAO eventDAO;

	@PersistenceContext
	private EntityManager em;

	@Override
	public void resourceCreated(AbstractResource entity)
	{
		post(entity, FeedEntryScenario.CREATE);
	}

	@Override
	public void resourceUpdated(AbstractResource entity)
	{
		post(entity, FeedEntryScenario.EDIT);
	}

	@Override
	public void resourceDeleted(AbstractResource entity)
	{
		post(entity, FeedEntryScenario.REMOVE);
	}

	@Override
	public Set<Account> findInterested(AbstractResource entity)
	{
		Set<Account> results = new HashSet<Account>();
		results.addAll(resourceDAO.findManagers(entity));
		if (entity instanceof PersistentEvent)
		{
			results.addAll(eventDAO.findParticipants((PersistentEvent) entity));
		}
		return results;
	}

	private void post(AbstractResource entity, FeedEntryScenario scenario)
	{
		persistEntry(entity, getCurrent(), scenario);
	}

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void persistEntry(final AbstractResource entity,
			final Account createdBy, final FeedEntryScenario scenario)
	{
		FeedEntry entry = new FeedEntry(entity, createdBy, scenario);
		entry.getAccounts().addAll(findInterested(entity));
		em.persist(entry);
	}

	@Override
	public Account getCurrent()
	{
		String id = SpringApplicationContext.getUser().getId();
		if (id == null)
		{
			return null;
		}
		return em.getReference(Account.class, id);
	}
}
