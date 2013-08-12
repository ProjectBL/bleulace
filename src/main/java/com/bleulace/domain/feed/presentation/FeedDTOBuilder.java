package com.bleulace.domain.feed.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean(gettersByDefault = false)
public class FeedDTOBuilder
{
	private List<FeedText> text = new ArrayList<FeedText>();
	private byte[] imageData;

	public FeedDTOBuilder add(String part)
	{
		text.add(new DefaultFeedText(part));
		return this;
	}

	public FeedDTOBuilder add(FeedText part)
	{
		text.add(part);
		return this;
	}

	public FeedDTOBuilder setImageData(byte[] imageData)
	{
		this.imageData = imageData;
		return this;
	}

	public FeedDTO build()
	{
		Assert.notEmpty(text);
		return new FeedDTOValue(text, imageData);
	}
}