package com.bleulace.utils.authz;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.AllPermission;

/**
 * A permission object that grants access to anything and everything, including
 * rock, roll, and the act of fucking shit up. Ideally this should only be used
 * for testing.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class PermissionToRock extends AllPermission
{
	private static final Permission INSTANCE = new PermissionToRock();

	private PermissionToRock()
	{
	}

	@Override
	public boolean equals(Object o)
	{
		return o == INSTANCE;
	}

	@Override
	public String toString()
	{
		return "*";
	}

	public static Permission fuckShitUp()
	{
		return INSTANCE;
	}
}