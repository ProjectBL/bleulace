package com.bleulace.ddd.spec;

import org.apache.shiro.authz.Permission;

/**
 * Enables the assembly of dynamic permissions at runtime.
 * 
 * @author Arleigh Dickerson
 * 
 */
public interface PermissionSpecification extends Permission
{
	PermissionSpecification and(Permission other);

	PermissionSpecification or(Permission other);

	PermissionSpecification not();
	
	static aspect Impl
	{
		public PermissionSpecification PermissionSpecification.and(Permission other)
		{
			return new AndPermission(this,other);
		}
		
		public PermissionSpecification PermissionSpecification.or(Permission other)
		{
			return new OrPermission(this,other);
		}
		
		public PermissionSpecification PermissionSpecification.not()
		{
			return new NotPermission(this);
		}
	}
}