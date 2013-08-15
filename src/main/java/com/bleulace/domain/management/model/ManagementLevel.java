package com.bleulace.domain.management.model;

import org.apache.shiro.authz.Permission;

public enum ManagementLevel implements Permission
{
	LOOP, MIX, OWN;

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof ManagementLevel)
		{
			return ((ManagementLevel) p).ordinal() <= this.ordinal();
		}
		return false;
	}

	public Permission on(String id)
	{
		return new ManagementPermission(this, id);
	}
}