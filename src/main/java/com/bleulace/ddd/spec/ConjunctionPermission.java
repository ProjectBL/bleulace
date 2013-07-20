package com.bleulace.ddd.spec;

import java.util.Collection;

import org.apache.shiro.authz.Permission;

public class ConjunctionPermission implements PermissionSpecification
{
	private Permission[] conjunction;

	public ConjunctionPermission(Permission... conjunction)
	{
		this.conjunction = conjunction;
	}

	public ConjunctionPermission(Collection<Permission> conjunction)
	{
		this(conjunction.toArray(new Permission[conjunction.size()]));
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