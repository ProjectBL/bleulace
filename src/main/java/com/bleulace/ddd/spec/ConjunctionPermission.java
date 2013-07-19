package com.bleulace.ddd.spec;

import org.apache.shiro.authz.Permission;

public class ConjunctionPermission extends CompositePermission
{
	private Permission[] conjunction;

	public ConjunctionPermission(Permission... conjunction)
	{
		this.conjunction = conjunction;
	}

	@Override
	public boolean implies(Permission p)
	{
		for (Permission permission : conjunction)
		{
			if (!permission.implies(p))
			{
				return false;
			}
		}
		return true;
	}
}