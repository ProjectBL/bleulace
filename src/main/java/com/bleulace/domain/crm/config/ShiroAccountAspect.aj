package com.bleulace.domain.crm.config;

import java.util.TimeZone;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.EntityManagerReference;

aspect ShiroAccountAspect
{
	private static final String TIMEZONE_KEY = "timezone";

	after(Subject subject)  returning() : call(* Subject+.login(..))
	&&!within(ShiroAccountAspect)
	&& target(subject) 
	{
		initializeSubject(subject);
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
					.getTimeZone().getTimeZone();
		}
		return TimeZone.getDefault();
	}

	private void Subject.setTimeZone(TimeZone tz)
	{
		Assert.notNull(tz);
		getSession().setAttribute(TIMEZONE_KEY, tz);
	}

	private void initializeSubject(Subject subject)
	{

	}
}