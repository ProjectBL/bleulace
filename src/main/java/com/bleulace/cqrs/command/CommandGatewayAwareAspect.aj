package com.bleulace.cqrs.command;

import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;

import com.bleulace.utils.ctx.SpringApplicationContext;

/**
 * Declares the gateway() method on implementors of the
 * {@link CommandGatewayAware} to grant clients unintrusive access to the
 * command gateway interface
 * 
 * @author Arleigh Dickerson
 * 
 */
@SuppressWarnings("unchecked")
aspect CommandGatewayAwareAspect
{
	private static final transient CommandGateway GATEWAY = SpringApplicationContext
			.getBean(CommandGateway.class);

	public <R> void CommandGatewayAware.send(Object command,
			CommandCallback<R> callback)
	{
		GATEWAY.send(command, callback);
	}

	public void CommandGatewayAware.send(Object command)
	{
		GATEWAY.send(command);
	}

	public <R> R CommandGatewayAware.sendAndWait(Object command)
	{
		return (R) GATEWAY.sendAndWait(command);
	}

	public <R> R CommandGatewayAware.sendAndWait(Object command, long timeout,
			TimeUnit unit)
	{
		return (R) GATEWAY.sendAndWait(command, timeout, unit);
	}
}