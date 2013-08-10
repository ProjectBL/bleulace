package com.bleulace.domain.crm.model;

import java.util.Date;

import org.axonframework.common.annotation.MetaData;

import com.bleulace.cqrs.ShiroMetaData;
import com.bleulace.domain.crm.command.CommentCommand;
import com.bleulace.domain.crm.event.CommentedEvent;
import com.bleulace.jpa.EventSourcedAggregateRootMixin;

public interface CommentableRoot extends EventSourcedAggregateRootMixin
{
	static aspect Impl
	{
		public void CommentableRoot.handle(CommentCommand command,
				@MetaData(ShiroMetaData.SUBJECT_ID) String accountId,
				@MetaData(ShiroMetaData.TIMESTAMP) Date timestamp)
		{
			CommentedEvent event = new CommentedEvent(this.getId(), timestamp);
			event.setAccountId(accountId);
			this.apply(command, event);
		}
	}
}