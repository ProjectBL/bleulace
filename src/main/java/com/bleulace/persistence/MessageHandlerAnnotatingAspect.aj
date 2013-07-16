package com.bleulace.persistence;

import java.io.Serializable;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.cqrs.event.EventBusAware;
import com.bleulace.utils.ctx.SpringApplicationContext;

/**
 * Enables implementor of {@link EventBusAware} to acquire a reference to the
 * EventBus by calling the eventBus() method. Also declares classes which appear
 * to be intended for use as event payloads as Serializable.
 * 
 * @author Arleigh Dickerson
 * 
 */
public aspect MessageHandlerAnnotatingAspect
{
	interface Command
	{
	}

	interface Event extends Serializable
	{
	}
	
	declare parents : com.bleulace..event..*Event implements Event;
	declare parents : com.bleulace..command..*Command implements Command;

	declare @method : public void com.bleulace..*.on*(Event+) : @EventHandler;
	declare @method : public * com.bleulace..*.handle*(Command+) : @CommandHandler;
	declare @constructor : public com.bleulace..new(Command+) : @CommandHandler;

	private static final transient EventBus EVENTBUS = SpringApplicationContext
			.get().getBean(EventBus.class);

	/**
	 * 
	 * @return a configured event bus ready for use
	 */
	EventBus EventBusAware.eventBus()
	{
		return EVENTBUS;
	}
}