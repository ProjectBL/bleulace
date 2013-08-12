package com.bleulace.domain.feed.presentation;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class HyperlinkedFeedText extends DefaultFeedText
{
	private final String navState;

	public HyperlinkedFeedText(String value, String navState)
	{
		super(value);
		Assert.notNull(navState);
		this.navState = navState;
	}
}
