package com.bleulace.domain.crm.config;

import java.io.Serializable;
import java.util.TimeZone;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.web.SecurityContext;

aspect ShiroAccountAspect
{
	private static SecurityContext ctx;

	@Autowired(required = false)
	public void setSecurityContext(SecurityContext context)
	{
		ctx = context;
	}

	private static final String TIMEZONE_KEY = "timezone";

	declare parents : DelegatingSubject extends Serializable;

	declare warning : call(public * SecurityUtils.*(..)) && within(com.bleulace..*) : 
		"don't do that. Use a SecurityContext bean to get references to Subject.";

	Subject around() : call(Subject SecurityUtils.getSubject()) && 
	if(ctx != null)
	{
		try
		{
			return ctx.getSubject();
		}
		catch (Exception e)
		{
			e.printStackTrace();
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