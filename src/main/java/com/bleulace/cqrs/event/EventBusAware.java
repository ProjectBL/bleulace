package com.bleulace.cqrs.event;

import org.axonframework.eventhandling.EventBus;

/**
 * Implementors of this mixin can acquire a reference to the Axon event bus,
 * {@link EventBus}, by calling Self.eventBus()
 * 
 * @author Arleigh Dickerson
 * 
 */
public interface EventBusAware
{
}