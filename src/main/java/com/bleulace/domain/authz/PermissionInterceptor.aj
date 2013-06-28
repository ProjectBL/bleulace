package com.bleulace.domain.authz;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.util.ReflectionUtils;

public abstract aspect PermissionInterceptor
{
	abstract pointcut guardedMethodExecution(Object target);

	before(Object target) : guardedMethodExecution(target)
	{
		SecurityUtils.getSubject().checkPermissions(
				getRuntimePermissions(target));
	}

	protected Set<Permission> getRuntimePermissions(Object target)
	{
		Set<Permission> permissions = new HashSet<Permission>();
		permissions.addAll(getFieldPermissions(target));
		permissions.addAll(getMethodPermissions(target));
		return permissions;
	}

	private Set<Permission> getMethodPermissions(Object target)
	{
		Class<?> clazz = target.getClass();
		Set<Permission> permissions = new HashSet<Permission>();
		for (Method method : clazz.getMethods())
		{
			if (method.getAnnotation(PermissionValue.class) != null)
			{
				permissions.addAll(resolveObjectPermissions(ReflectionUtils
						.invokeMethod(method, target)));
			}
		}
		return permissions;
	}

	private Set<Permission> getFieldPermissions(Object target)
	{
		Class<?> clazz = target.getClass();
		Set<Permission> permissions = new HashSet<Permission>();
		for (Field field : clazz.getFields())
		{
			if (field.getAnnotation(PermissionValue.class) != null)
			{
				permissions.addAll(resolveObjectPermissions(ReflectionUtils
						.getField(field, target)));
			}
		}
		return permissions;
	}

	private Set<Permission> resolveObjectPermissions(Object obj)
	{
		Set<Permission> permissions = new HashSet<Permission>();
		if (obj != null)
		{
			if (obj.getClass().isAssignableFrom(Iterable.class))
			{
				for (Object member : (Iterable<?>) obj)
				{
					permissions.addAll(resolveObjectPermissions(member));
				}
			}
			if (obj.getClass().isAssignableFrom(Permission.class))
			{
				permissions.add((Permission) obj);
			}
			if (obj.getClass().isAssignableFrom(String.class))
			{
				permissions.add(new WildcardPermission((String) obj));
			}
		}
		return permissions;
	}
}