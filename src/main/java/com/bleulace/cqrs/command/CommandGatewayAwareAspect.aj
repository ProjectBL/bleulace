package com.bleulace.cqrs.command;

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
	private static final transient MasterCommandGateway GATEWAY = SpringApplicationContext
			.getBean(MasterCommandGateway.class);

	/**
	 * 
	 * @return a configured command gateway ready for use
	 */
	MasterCommandGateway CommandGatewayAware.gateway()
	{
		return GATEWAY;
	}
}