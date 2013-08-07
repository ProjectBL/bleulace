package com.bleulace.persistence;

import java.io.Serializable;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.cqrs.event.EventBusAware;

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
	interface Command extends Serializable
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
}