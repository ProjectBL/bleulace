package com.bleulace.domain.crm.model;

import com.bleulace.cqrs.EventSourcedAggregateRootMixin;
import com.bleulace.domain.crm.command.CommentCommand;

public interface CommentableRoot extends EventSourcedAggregateRootMixin
{
	static aspect Impl
	{
		public void CommentableRoot.handle(CommentCommand command,
				org.axonframework.domain.MetaData metaData)
		{
			this.apply(command, metaData);
		}
	}
}