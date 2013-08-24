package com.bleulace.domain.resource.model;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.cqrs.DomainEventPayload;
import com.bleulace.cqrs.EventSourcedEntityMixin;

aspect ResourcingAspect
{
	pointcut eventHandlerAnnotated() : 
		execution(@EventHandler * *.*(..));

	pointcut resourceExecution(EventSourcedEntityMixin resource) : 
		execution(* *.*(..))
		&& this(resource+);

	pointcut eventArgs(DomainEventPayload event) : 
		execution(* *.*(DomainEventPayload+,..))
		&& args(event+);

	void around(EventSourcedEntityMixin resource, DomainEventPayload payload) : 
		eventHandlerAnnotated() 
		&& eventArgs(payload+) 
		&& resourceExecution(resource+)
	{
		if (payload.getId() == null || resource.getId() == null
				|| payload.getId().equals(resource.getId()))
		{
			proceed(resource, payload);
		}
	}
}