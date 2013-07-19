package com.bleulace.ddd.spec;

import org.apache.shiro.authz.Permission;

public class AndPermission extends CompositePermission
{
	private final Permission a;
	private final Permission b;

	public AndPermission(Permission a, Permission b)
	{
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean implies(Permission candidate)
	{
		return a.implies(candidate) && b.implies(candidate);
	}
}
