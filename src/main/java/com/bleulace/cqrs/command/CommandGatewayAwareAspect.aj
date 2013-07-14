package com.bleulace.cqrs.command;

import com.bleulace.utils.SpringApplicationContext;

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
	private static final transient MasterCommandGateway GATEWAY = SpringApplicationContext
			.get().getBean(MasterCommandGateway.class);

	/**
	 * 
	 * @return a configured command gateway ready for use
	 */
	MasterCommandGateway CommandGatewayAware.gateway()
	{
		return GATEWAY;
	}
}