package com.bleulace.domain.crm.model;

import org.modelmapper.ModelMapper;

import com.bleulace.domain.crm.event.CommentedEvent;
import com.bleulace.domain.resource.model.CompositeResource;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.utils.jpa.EntityManagerReference;

public interface CommentableResource extends CompositeResource
{
	static aspect Impl
	{
		public void CommentableResource.on(CommentedEvent event)
		{
			Comment c = new Comment();
			SpringApplicationContext.getBean(ModelMapper.class).map(event, c);
			c.setAuthor(EntityManagerReference.load(Account.class,
					event.getAccountId()));
			this.addChild(c);
		}
	}
}
