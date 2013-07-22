package com.bleulace.utils.authz;

import org.apache.shiro.authz.Permission;

public class OrPermission implements PermissionSpecification
{
	private final Permission a;
	private final Permission b;

	public OrPermission(Permission a, Permission b)
	{
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean implies(Permission p)
	{
		return a.implies(p) || b.implies(p);
	}
}
