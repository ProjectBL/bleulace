package com.bleulace.mgt.domain;

import org.apache.shiro.authz.Permission;

import com.bleulace.ddd.spec.PermissionSpecification;
import com.bleulace.utils.jpa.EntityManagerReference;

public class ResourcePermission extends SingleResourcePermission
{
	public ResourcePermission(Resource resource, ManagementAssignment assignment)
	{
		super(resource, assignment);
	}

	@Override
	public boolean implies(Permission p)
	{
		PermissionSpecification permission = this;
		permission = permission.and(new SingleResourcePermission(getResource(),
				getAssignment()));
		for (String id : getResource().getChildIds())
		{
			permission = permission.and(new SingleResourcePermission(
					EntityManagerReference.get().getReference(Resource.class,
							id), getAssignment()));
		}

		return permission.implies(p);
	}
}
