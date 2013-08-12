package com.bleulace.cqrs;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;


public interface DomainEventPayload extends MessagePayload
{
	static aspect Impl
	{
		private String DomainEventPayload.id;

		@TargetAggregateIdentifier
		public String DomainEventPayload.getId()
		{
			return id;
		}
		
		public void DomainEventPayload.setId(String id)
		{
			//Assert.notNull(id);
			this.id = id;
		}
	}
}