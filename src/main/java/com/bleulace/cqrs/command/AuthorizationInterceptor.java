package com.bleulace.cqrs.command;

import java.lang.annotation.Annotation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.GuestAnnotationHandler;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;
import org.apache.shiro.authz.aop.UserAnnotationHandler;
import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;

import com.bleulace.cqrs.AuthorizableCommandPayload;

/**
 * Checks authorizations before dispatching commands implementing the
 * {@link Permission} interface.
 * 
 * @author Arleigh Dickerson
 * 
 */
class AuthorizationInterceptor implements CommandDispatchInterceptor
{
	//@formatter:off
	private AuthorizingAnnotationHandler[] annotationHandlers = new AuthorizingAnnotationHandler[] {
			new AuthenticatedAnnotationHandler(),
			new GuestAnnotationHandler(),
			new PermissionAnnotationHandler(),
			new RoleAnnotationHandler(),
			new UserAnnotationHandler()
	};
	//@formatter:on

	@Override
	public CommandMessage<?> handle(CommandMessage<?> commandMessage)
	{
		Object payload = commandMessage.getPayload();

		for (Annotation a : payload.getClass().getAnnotations())
		{
			for (AuthorizingAnnotationHandler handler : annotationHandlers)
			{
				handler.assertAuthorized(a);
			}
		}

		if (payload instanceof AuthorizableCommandPayload)
		{
			Permission permission = ((AuthorizableCommandPayload) payload)
					.getPermission();

			// throw exception on authorization failure
			SecurityUtils.getSubject().checkPermission(permission);
		}
		return commandMessage;
	}
}