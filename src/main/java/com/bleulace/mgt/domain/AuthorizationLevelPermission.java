package com.bleulace.mgt.domain;

import org.apache.shiro.authz.Permission;
import org.springframework.util.Assert;

public class AuthorizationLevelPermission implements Permission
{
	private final Authorizable authorizable;
	private final AuthorizationLevel level;

	public AuthorizationLevelPermission(Authorizable authorizable,
			AuthorizationLevel level)
	{
		Assert.noNullElements(new Object[] { authorizable, level });
		this.authorizable = authorizable;
		this.level = level;
	}

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof AuthorizationLevelPermission)
		{
			AuthorizationLevelPermission permission = (AuthorizationLevelPermission) p;
			if (level.ordinal() >= permission.level.ordinal())
			{
				Authorizable cursor = permission.authorizable;
				do
				{
					cursor = cursor.getParent();
					if (cursor.equals(authorizable))
					{
						return true;
					}
				}
				while (cursor != null);
			}
		}
		return false;
	}
}