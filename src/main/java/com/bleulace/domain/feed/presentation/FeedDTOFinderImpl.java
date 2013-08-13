package com.bleulace.domain.feed.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.feed.model.FeedEntry;

@Component
@Profile({ "dev", "prod" })
class FeedDTOFinderImpl implements FeedDTOFinder
{
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FeedDTOFactoryLocater locater;

	@Override
	public List<FeedDTO> getFeed(String viewerId, String targetId)
	{
		Account viewer = em.getReference(Account.class, viewerId);
		List<FeedEntry> entries = em.getReference(Account.class, targetId)
				.getFeedEntries();
		List<FeedDTO> dtos = new ArrayList<FeedDTO>();
		for (FeedEntry entry : entries)
		{
			FeedDTOFactory factory = locater.locate(entry.getGeneratingEvent()
					.getClass());
			if (factory != null)
			{
				dtos.add(factory.make(entry, viewer));
			}
		}
		return dtos;
	}
}
