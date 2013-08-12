package com.bleulace.domain.feed.infrastructure;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.feed.model.FeedEntry;

@RooJavaBean
public class FeedMessage
{
	private final FeedEntry content;

	private final String[] destinations;

	public FeedMessage(FeedEntry content, String... destination)
	{
		this.content = content;
		this.destinations = destination;
	}
}
