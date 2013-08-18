package com.bleulace.utils.authz;

import org.apache.shiro.authz.Permission;

public interface GenericPermission<T extends GenericPermission<T>> extends
		Permission
{
	public boolean implies(T p);

	static abstract aspect Impl<T>
	{
		@SuppressWarnings("unchecked")
		public boolean GenericPermission.implies(Permission p)
		{
			if (getClass().isAssignableFrom(p.getClass()))
			{
				return implies((T) p);
			}
			return false;
		}
	}
}