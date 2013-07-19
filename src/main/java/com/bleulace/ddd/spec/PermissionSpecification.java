package com.bleulace.ddd.spec;

import org.apache.shiro.authz.Permission;

public interface PermissionSpecification extends Permission
{
	PermissionSpecification and(Permission other);

	PermissionSpecification or(Permission other);

	PermissionSpecification not();
}