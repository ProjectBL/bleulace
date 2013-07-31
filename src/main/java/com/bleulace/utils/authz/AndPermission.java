package com.bleulace.utils.authz;

import org.apache.shiro.authz.Permission;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
public class AndPermission implements PermissionSpecification
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