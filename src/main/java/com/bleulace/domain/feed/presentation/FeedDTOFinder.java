package com.bleulace.domain.feed.presentation;

import java.util.List;

public interface FeedDTOFinder
{
	public List<FeedDTO> getFeed(String viewer, String targetId);
}