package com.bleulace.crm.infrastructure;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.bleulace.crm.domain.Account;
import com.bleulace.utils.jpa.EntityManagerReference;

public aspect ShiroAccountAspect
{
	public String Subject.getId()
	{
		return getPrincipal() == null ? null : (String) getPrincipal();
	}

	public static Account SecurityUtils.getAccount()
	{
		return SecurityUtils.getSubject().getAccount();
	}

	public Account Subject.getAccount()
	{
		if (getId() != null)
		{
			return EntityManagerReference.load(Account.class, getId());
		}
		return null;
	}

	Object around() : execution(public Object Subject.getPrincipal()) 
	{
		Object retVal = null;
		try
		{
			return proceed();
		}
		catch (Throwable t)
		{
			return null;
		}
	}
}