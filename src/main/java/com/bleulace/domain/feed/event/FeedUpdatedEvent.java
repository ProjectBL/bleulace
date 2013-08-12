package com.bleulace.domain.feed.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class FeedUpdatedEvent
{
	private final String id;

	public FeedUpdatedEvent(String id)
	{
		this.id = id;
	}
}
