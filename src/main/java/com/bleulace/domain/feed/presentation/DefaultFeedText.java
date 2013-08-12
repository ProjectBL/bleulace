package com.bleulace.domain.feed.presentation;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class DefaultFeedText implements FeedText
{
	private final String value;

	public DefaultFeedText(String value)
	{
		Assert.notNull(value);
		this.value = value;
	}
}