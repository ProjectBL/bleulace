package com.bleulace.domain.authz;

public aspect PermissionValuesGuardingAspect extends PermissionInterceptor
{
	pointcut guardedMethodExecution(Object target) : 
		annotatedMethodExecution(target) 
		|| annotatedTypeExecution(target);

	pointcut annotatedMethodExecution(Object target) : 
			execution(@RequiresPermissionValues * *.*(..))
			&& this(target);

	pointcut annotatedTypeExecution(Object target) : 
			execution(* TypeRequiresRuntimePermission+.*(..)) && this(target);

	declare parents : @RequiresPermissionValues * implements TypeRequiresRuntimePermission;

	private interface TypeRequiresRuntimePermission
	{
	}
}
