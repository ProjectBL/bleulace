package com.bleulace.mgt.domain;

import org.apache.shiro.authz.Permission;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.ddd.spec.PermissionSpecification;

@RooEquals
@RooJavaBean(settersByDefault = false)
class SingleManagementPermission implements PermissionSpecification
{
	static final long serialVersionUID = -5681890426894240536L;

	private ManagementAssignment assignment;

	private Project project;

	SingleManagementPermission()
	{
	}

	public SingleManagementPermission(Project project,
			ManagementAssignment assignment)
	{
		Assert.noNullElements(new Object[] { project, assignment });
		this.assignment = assignment;
		this.project = project;
	}

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof SingleManagementPermission)
		{
			SingleManagementPermission that = (SingleManagementPermission) p;

			if (that.project.getId() == null)
			{
				throw new IllegalArgumentException();
			}

			return this.project.getId().equals(that.project.getId())
					&& this.assignment.implies(that.assignment);
		}
		return false;
	}
}