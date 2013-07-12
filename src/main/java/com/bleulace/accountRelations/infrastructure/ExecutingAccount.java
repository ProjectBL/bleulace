package com.bleulace.accountRelations.infrastructure;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.springframework.stereotype.Component;

import com.bleulace.accountRelations.domain.Account;
import com.bleulace.persistence.utils.EntityManagerReference;

@Component
public class ExecutingAccount
{
	public Account current()
	{
		try
		{
			Object id = SecurityUtils.getSubject().getPrincipal();
			if (id != null)
			{
				return EntityManagerReference.get().getReference(Account.class,
						id);
			}
		}
		catch (UnavailableSecurityManagerException e)
		{
		}
		return null;
	}
}