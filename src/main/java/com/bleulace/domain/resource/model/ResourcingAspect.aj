package com.bleulace.domain.resource.model;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.cqrs.DomainEventPayload;

aspect ResourcingAspect
{
	pointcut eventHandlerAnnotated() : 
		execution(@EventHandler * *.*(..));

	pointcut resourceExecution(Resource resource) : 
		execution(* *.*(..))
		&& this(resource+);

	pointcut eventArgs(DomainEventPayload event) : 
		execution(* *.*(*,..))
		&& args(event);

	void around(Resource resource, DomainEventPayload payload) : 
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