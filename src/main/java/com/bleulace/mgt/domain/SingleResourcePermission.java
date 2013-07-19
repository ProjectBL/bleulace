package com.bleulace.mgt.domain;

import org.apache.shiro.authz.Permission;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.ddd.spec.CompositePermission;

@RooJavaBean(settersByDefault = false)
public class SingleResourcePermission extends CompositePermission
{
	static final long serialVersionUID = -5681890426894240536L;

	private final ManagementAssignment assignment;
	private final Resource resource;

	public SingleResourcePermission(Resource resource,
			ManagementAssignment assignment)
	{
		Assert.noNullElements(new Object[] { resource, assignment });
		this.assignment = assignment;
		this.resource = resource;
	}

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof SingleResourcePermission)
		{
			SingleResourcePermission that = (SingleResourcePermission) p;

			if (that.resource.getId() == null)
			{
				throw new IllegalArgumentException();
			}

			return this.resource.getId().equals(that.resource.getId())
					&& this.assignment.implies(that.assignment);
		}
		return false;
	}
}