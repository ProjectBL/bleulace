package com.bleulace.domain.resource.model;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.cqrs.DomainEventPayload;
import com.bleulace.cqrs.EventSourcedEntityMixin;

aspect ResourcingAspect
{
	pointcut eventHandlerAnnotated() : 
		execution(@EventHandler * *.*(..));

	pointcut eventSourcedExecution(EventSourcedEntityMixin entity) : 
		execution(* *.*(..))
		&& this(entity);

	pointcut eventArgs(DomainEventPayload event) : 
		execution(* *.*(DomainEventPayload+,..))
		&& args(event);

	void around(EventSourcedEntityMixin entity, DomainEventPayload payload) : 
		eventHandlerAnnotated() 
		&& eventArgs(payload) 
		&& eventSourcedExecution(entity)
	{
		if (payload.getId() == null || entity.getId() == null
				|| payload.getId().equals(entity.getId()))
		{
			proceed(entity, payload);
		}
	}
}