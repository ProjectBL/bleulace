package com.bleulace.domain.management.model;

import org.apache.shiro.authz.Permission;

import com.bleulace.utils.authz.GenericPermission;

public enum ManagementRole implements GenericPermission<ManagementRole>
{
	LOOP, MIX, OWN;

	@Override
	public boolean implies(ManagementRole level)
	{
		return this.ordinal() >= level.ordinal();
	}

	public Permission on(String id)
	{
		return new ManagementPermission(this, id);
	}
}