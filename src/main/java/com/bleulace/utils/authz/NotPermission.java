package com.bleulace.utils.authz;

import org.apache.shiro.authz.Permission;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
public class NotPermission implements PermissionSpecification
{
	private Permission wrapped;

	public NotPermission(Permission wrapped)
	{
		this.wrapped = wrapped;
	}

	@Override
	public boolean implies(Permission p)
	{
		return !wrapped.implies(p);
	}
}