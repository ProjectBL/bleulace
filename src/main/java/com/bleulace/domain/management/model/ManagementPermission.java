package com.bleulace.domain.management.model;

import org.apache.shiro.authz.Permission;
import org.springframework.roo.addon.equals.RooEquals;

import com.bleulace.domain.resource.model.ResourcePermission;

@RooEquals
class ManagementPermission implements Permission
{
	private final String id;

	private final ManagementLevel level;

	ManagementPermission(ManagementLevel level, String id)
	{
		this.level = level;
		this.id = id;
	}

	@Override
	public boolean implies(Permission p)
	{
		if (this.equals(p))
		{
			return true;
		}

		return impliesManagementLevel(p) || impliesResource(p);
	}

	private boolean impliesManagementLevel(Permission p)
	{
		if (p instanceof ManagementLevel)
		{
			ManagementLevel that = (ManagementLevel) p;
			return this.level.implies(that);
		}
		else if (p instanceof ManagementPermission)
		{
			ManagementLevel that = ((ManagementPermission) p).level;
			return impliesManagementLevel(that);
		}
		return false;
	}

	private boolean impliesResource(Permission p)
	{
		if (p instanceof ResourcePermission)
		{
			ResourcePermission that = (ResourcePermission) p;
			return new ResourcePermission(id).implies(that);
		}
		if (p instanceof ManagementPermission)
		{
			ManagementPermission that = (ManagementPermission) p;
			return implies(new ResourcePermission(that.id));
		}
		return false;
	}

	@Override
	public String toString()
	{
		return level + ":" + id;
	}

}