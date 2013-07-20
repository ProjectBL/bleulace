package com.bleulace.ddd.spec;

import org.apache.shiro.authz.Permission;

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