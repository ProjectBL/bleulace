package com.bleulace.domain.management.infrastructure;

import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.feed.model.FeedEntry;
import com.bleulace.domain.feed.presentation.FeedDTO;
import com.bleulace.domain.feed.presentation.FeedDTOBuilder;
import com.bleulace.domain.feed.presentation.FeedDTOFactory;
import com.bleulace.domain.management.event.ProjectCreatedEvent;

@Component
class ProjectCreatedFeedDTOFactory implements FeedDTOFactory
{
	@Override
	public FeedDTO make(FeedEntry entry, Account viewer)
	{
		return new FeedDTOBuilder().add("Project created.").build();
	}

	@Override
	public Class<?> getEventClass()
	{
		return ProjectCreatedEvent.class;
	}

}
