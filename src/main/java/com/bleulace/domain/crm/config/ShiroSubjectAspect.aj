package com.bleulace.domain.crm.config;

import java.util.TimeZone;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.EntityManagerReference;

aspect ShiroSubjectAspect
{
	private transient static SecurityContext ctx;

	@Autowired(required = false)
	public void setSecurityContext(SecurityContext context)
	{
		ctx = context;
	}

	private static final String TIMEZONE_KEY = "timezone";

	Subject around() : call(Subject SecurityUtils.getSubject()) && 

	if(ctx != null)
	{
		try
		{
			return ctx.getSubject();
		}
		catch (Exception e)
		{
			return proceed();
		}
	}

	boolean around(Permission p) : 
		execution(public boolean Permission.implies(Permission+)) 
		&& args(p)
	{
		if (p == null)
		{
			return false;
		}
		return proceed(p);
	}

	public String Subject.getId()
	{
		return (String) getPrincipal();
	}

	public void Subject.login(String username, String password)
	{
		login(username, password, false);
	}

	public void Subject.login(String username, String password,
			boolean rememberMe)
	{
		login(new UsernamePasswordToken(username, password, rememberMe));
	}

	public TimeZone Subject.getTimeZone()
	{
		if (isAuthenticated())
		{
			return EntityManagerReference.load(Account.class, getId())
					.getTimeZone();
		}
		return TimeZone.getDefault();
	}

	private void Subject.setTimeZone(TimeZone tz)
	{
		Assert.notNull(tz);
		getSession().setAttribute(TIMEZONE_KEY, tz);
	}
}