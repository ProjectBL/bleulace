package com.bleulace.crm.infrastructure;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.Account;
import com.bleulace.persistence.utils.EntityManagerReference;

/**
 * An application component to find the Account belonging to the currently
 * executing subject.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
public class ExecutingAccount
{
	/**
	 * 
	 * @return the Account belonging to the currently executing subject iff the
	 *         subject is authenticated or remembered from a previous session.
	 *         Otherwise return null, representing an unauthenticated, anonymous
	 *         system user.
	 */
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