package com.bleulace.utils.authz;

import java.util.Collection;

import org.apache.shiro.authz.Permission;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
public class DisjunctionPermission implements PermissionSpecification
{
	private Permission[] disjunction;

	public DisjunctionPermission(Collection<Permission> disjunction)
	{
		this(disjunction.toArray(new Permission[disjunction.size()]));
	}

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