package com.bleulace.cqrs.command;

/**
 * Implementors of this mixin will have access to the Bleulace command gateway,
 * {@link MasterCommandGateway}, by calling Self.gateway()
 * 
 * 
 * @author Arleigh Dickerson
 * 
 */
public interface CommandGatewayAware
{
}