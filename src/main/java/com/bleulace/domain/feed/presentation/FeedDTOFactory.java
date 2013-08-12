package com.bleulace.domain.feed.presentation;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.feed.model.FeedEntry;

public interface FeedDTOFactory
{
	FeedDTO make(FeedEntry entry, Account viewer);

	Class<?> getEventClass();
}