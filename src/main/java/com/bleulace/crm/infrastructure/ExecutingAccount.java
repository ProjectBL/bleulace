package com.bleulace.crm.infrastructure;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;

import com.bleulace.crm.domain.Account;
import com.bleulace.utils.jpa.EntityManagerReference;

/**
 * An application component to find the Account belonging to the currently
 * executing subject.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class ExecutingAccount
{
	/**
	 * 
	 * @return the Account belonging to the currently executing subject iff the
	 *         subject is authenticated or remembered from a previous session.
	 *         Otherwise return null, representing an unauthenticated, anonymous
	 *         system user.
	 */
	public static Account current()
	{
		try
		{
			Object id = SecurityUtils.getSubject().getPrincipal();
			if (id != null)
			{
				try
				{
					return EntityManagerReference.get().getReference(
							Account.class, id);
				}
				catch (Exception e)
				{
					// TODO : hit the logs
					// TODO : fire off warning email
					e.printStackTrace();
				}
			}
		}
		catch (UnavailableSecurityManagerException e)
		{
			// TODO : hit the logs
			// TODO : fire off warning email
		}
		return null;
	}

	public static String id()
	{
		Account account = current();
		return account == null ? null : account.getId();
	}
}