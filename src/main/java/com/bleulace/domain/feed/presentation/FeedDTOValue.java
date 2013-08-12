package com.bleulace.domain.feed.presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
class FeedDTOValue implements FeedDTO
{
	private final List<FeedText> content = new ArrayList<FeedText>();

	private final byte[] imageData;

	@Override
	public Iterator<FeedText> iterator()
	{
		return content.iterator();
	}

	@Override
	public byte[] getImageData()
	{
		return null;
	}

	public FeedDTOValue(Collection<FeedText> content, byte[] imageData)
	{
		Assert.notNull(content);
		Assert.notEmpty(content);
		this.content.addAll(content);
		this.imageData = imageData;
	}

	public FeedDTOValue(Collection<FeedText> content)
	{
		this(content, null);
	}

}
