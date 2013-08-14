package com.bleulace.domain.crm.model;

import org.axonframework.domain.MetaData;

import com.bleulace.domain.crm.command.CommentCommand;
import com.bleulace.domain.resource.model.CompositeResource;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.dto.Mapper;

public interface CommentableResource extends CompositeResource
{
	static aspect Impl
	{
		public void CommentableResource.on(CommentCommand event,
				MetaData metaData)
		{
			Comment c = new Comment(event.getContent(),
					EntityManagerReference.load(Account.class,
							metaData.getSubjectId()), metaData.getTimestamp());
			Mapper.map(event, c);
			this.addChild(c);
		}
	}
}
