package com.bleulace.domain.crm.event;

import java.io.Serializable;

import org.axonframework.domain.MetaData;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.command.CommentCommand;
import com.bleulace.domain.feed.infrastructure.DefaultFeedEntryProvider;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.utils.jpa.EntityManagerReference;

@Component
class CommentEventFeedEntryProvider extends
		DefaultFeedEntryProvider<CommentCommand>
{

	public CommentEventFeedEntryProvider()
	{
		super(CommentCommand.class, true);
	}

	@Override
	public Serializable[] provideData(CommentCommand event, MetaData metaData)
	{
		return new Serializable[] { EntityManagerReference.load(
				AbstractResource.class, event.getId()) };
	}

}
