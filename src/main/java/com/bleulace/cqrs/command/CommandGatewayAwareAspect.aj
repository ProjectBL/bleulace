package com.bleulace.cqrs.command;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;

import com.bleulace.utils.ctx.SpringApplicationContext;

/**
 * Declares the gateway() method on implementors of the
 * {@link CommandGatewayAware} to grant clients unintrusive access to the
 * command gateway interface
 * 
 * @author Arleigh Dickerson
 * 
 */
aspect CommandGatewayAwareAspect
{
	private static final transient CommandGateway GATEWAY = SpringApplicationContext
			.getBean(CommandGateway.class);

	/**
	 * 
	 * @return a configured command gateway ready for use
	 */
	CommandGateway CommandGatewayAware.gateway()
	{
		return GATEWAY;
	}
}