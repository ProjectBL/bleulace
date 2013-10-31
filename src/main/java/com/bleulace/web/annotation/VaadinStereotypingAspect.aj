package com.bleulace.web.annotation;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.aspectj.lang.reflect.MethodSignature;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

aspect VaadinStereotypingAspect
{
	pointcut pushEnabled() : execution(@EnablePush * *(..));

	pointcut requiresAuthorization() : execution(@RequiresAuthorization * *(..));

	Object around() : pushEnabled()
	{
		PushCallback callback = new PushCallback()
		{
			public void run()
			{
				retVal = proceed();
			}
		};
		UI.getCurrent().access(callback);
		return callback.retVal;
	}

	private abstract class PushCallback implements Runnable
	{
		Object retVal = null;
	}

	Object around() : requiresAuthorization()
	{
		MethodSignature sig = (MethodSignature) thisJoinPoint.getSignature();
		RequiresAuthorization a = sig.getMethod().getAnnotation(
				RequiresAuthorization.class);
		String permission = getPermissionString(a);
		System.out.println(permission);
		try
		{
			SecurityUtils.getSubject().checkPermission(permission);
			return proceed();
		}
		catch (AuthorizationException e)
		{
			Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
			return null;
		}
	}

	private String getPermissionString(RequiresAuthorization annotation)
	{
		return assemble(annotation.types()) + ":"
				+ assemble(annotation.actions()) + ":"
				+ assemble(getTargetIds());
	}

	private String assemble(String[] args)
	{
		String value = "";
		for (int i = 0; i < args.length; i++)
		{
			value += args[i] + (i + 1 == args.length ? "" : ",");
		}
		return value;
	}

	private String[] getTargetIds()
	{
		List<String> ids = SpringApplicationContext.getUser().getTargetIds();
		return ids.toArray(new String[ids.size()]);
	}
}