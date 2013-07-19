package com.bleulace.ddd.spec;

import org.apache.shiro.authz.Permission;

public abstract class CompositePermission implements PermissionSpecification
{
	@Override
	public PermissionSpecification and(Permission other)
	{
		return new AndPermission(this, other);
	}

	@Override
	public PermissionSpecification or(Permission other)
	{
		return new OrPermission(this, other);
	}

	@Override
	public PermissionSpecification not()
	{
		return new NotPermission(this);
	}
}
