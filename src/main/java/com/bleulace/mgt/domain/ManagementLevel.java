package com.bleulace.mgt.domain;

import org.apache.shiro.authz.Permission;

/**
 * A --CRUCIAL-- component of the application, this enum describes permissions
 * for both the reading and writing of client's projects and client's project's
 * components. @see the mockups
 * 
 * @author Arleigh Dickerson
 * 
 */
public enum ManagementLevel implements Permission
{
	LOOP, MIX, OWN;

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof ManagementLevel)
		{
			return ordinal() >= ((ManagementLevel) p).ordinal();
		}
		return false;
	}
}