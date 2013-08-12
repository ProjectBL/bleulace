package com.bleulace.domain.feed.presentation;

public interface FeedDTO extends Iterable<FeedText>
{
	public byte[] getImageData();
}