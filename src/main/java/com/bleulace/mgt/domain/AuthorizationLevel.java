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
public enum AuthorizationLevel implements Permission
{
	LOOP, MIX, OWN;

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof AuthorizationLevel)
		{
			return ordinal() >= ((AuthorizationLevel) p).ordinal();
		}
		return false;
	}
}