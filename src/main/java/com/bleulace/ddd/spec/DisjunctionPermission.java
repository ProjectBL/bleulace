package com.bleulace.ddd.spec;

import org.apache.shiro.authz.Permission;

public class DisjunctionPermission extends CompositePermission
{
	private Permission[] disjunction;

	public DisjunctionPermission(Permission... disjunction)
	{
		this.disjunction = disjunction;
	}

	@Override
	public boolean implies(Permission p)
	{
		for (Permission permission : disjunction)
		{
			if (permission.implies(p))
			{
				return true;
			}
		}
		return false;
	}
}