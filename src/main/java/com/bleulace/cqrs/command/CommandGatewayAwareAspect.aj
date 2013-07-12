package com.bleulace.cqrs.command;

import com.bleulace.context.utils.SpringApplicationContext;

/**
 * Declares the gateway() method on implementors of the
 * {@link CommandGatewayAware} to allow these clients to unobtrusively access
 * the command gateway interface
 * 
 * @author Arleigh Dickerson
 * 
 */
aspect CommandGatewayAwareAspect
{
	private static final transient BLCommandGateway GATEWAY = SpringApplicationContext
			.get().getBean(BLCommandGateway.class);

	/**
	 * 
	 * @return a configured command gateway ready for use
	 */
	BLCommandGateway CommandGatewayAware.gateway()
	{
		return GATEWAY;
	}
}