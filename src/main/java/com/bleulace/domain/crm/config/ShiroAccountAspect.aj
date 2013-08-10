package com.bleulace.domain.crm.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.bleulace.domain.crm.model.AuthenticationTrace;

aspect ShiroAccountAspect
{
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
		AuthenticationException ex = null;
		boolean success = true;
		try
		{
			login(new UsernamePasswordToken(username, password, rememberMe));
		}
		catch (AuthenticationException e)
		{
			success = false;
			ex = e;
		}
		finally
		{
			//AuthenticationTrace.log(username, success, getSession().getHost());
		}
		if (ex != null)
		{
			throw ex;
		}
	}
}