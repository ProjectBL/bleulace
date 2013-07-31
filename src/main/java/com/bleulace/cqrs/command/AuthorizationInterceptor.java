package com.bleulace.cqrs.command;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;

/**
 * Checks authorizations before dispatching commands implementing the
 * {@link Permission} interface.
 * 
 * @author Arleigh Dickerson
 * 
 */
class AuthorizationInterceptor implements CommandDispatchInterceptor
{
	@Override
	public CommandMessage<?> handle(CommandMessage<?> commandMessage)
	{
		Object payload = commandMessage.getPayload();
		if (payload instanceof Permission)
		{
			Permission permission = (Permission) payload;

			// throw exception on authorization failure
			SecurityUtils.getSubject().checkPermission(permission);
		}
		return commandMessage;
	}
}