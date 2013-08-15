package com.bleulace.cqrs;

import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.cqrs.event.EventBusAware;

/**
 * Enables implementor of {@link EventBusAware} to acquire a reference to the
 * EventBus by calling the eventBus() method. Also declares classes which appear
 * to be intended for use as event payloads as Serializable.
 * 
 * @author Arleigh Dickerson
 * 
 */
public aspect MessageHandlerAnnotatingAspect implements CommandGatewayAware
{
	declare parents : com.bleulace..event..*Event implements DomainEventPayload;
	declare parents : com.bleulace..command..*Command implements CommandPayload;

	declare @method : public void com.bleulace..*.on*(DomainEventPayload+,..) : @EventHandler;
	declare @method : public void com.bleulace..*.on*(CommandPayload+,..) : @EventHandler;

	declare @method : public * com.bleulace..*.handle*(CommandPayload+,..) : @CommandHandler;
	declare @constructor : public com.bleulace..new(CommandPayload+,..) : @CommandHandler;

	pointcut resourceEventHandler(EventSourcedEntityMixin resource,
			DomainEventPayload event) : 
		execution(@EventHandler void EventSourcedEntityMixin+.*(..,DomainEventPayload+,..))
		&& target(resource)
		&& args(event);

	pointcut sendAnnotatedMethod() : 
		execution(@Send Object+ *.*(..));

	pointcut sendAnnotatedClass() : 
		execution(CommandPayload+ *.*(..)) && within(@Send *);
	
	after() returning(Object commandPayload) : 
		sendAnnotatedMethod() || sendAnnotatedClass()
	{
		Method method = ((MethodSignature) thisJoinPoint.getSignature())
				.getMethod();

		Send annotation = method.getAnnotation(Send.class);
		if (annotation == null)
		{
			annotation = method.getDeclaringClass().getAnnotation(Send.class);
		}
		
		if(annotation.async())
		{
			send(commandPayload);
		}
		else
		{
			sendAndWait(commandPayload);
		}
	}

	void around(EventSourcedEntityMixin resource, DomainEventPayload event) : 
		resourceEventHandler(resource,event)
	{
		String eventId = event.getId();
		if (eventId == null || resource.getId() == null
				|| resource.getId().equals(eventId))
		{
			proceed(resource, event);
		}
	}
}