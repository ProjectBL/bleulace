package com.bleulace.mgt.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;

public interface AuthorizableMixin extends Authorizable
{
	static aspect Impl
	{
		private Map<Account, AuthorizationLevel> Authorizable.authzMap = new HashMap<Account, AuthorizationLevel>();

		public void AuthorizableMixin.grant(AuthorizationLevel level,
				Account... accounts)
		{
			Assert.noNullElements(new Object[]{level,accounts});
			for (Account account : accounts)
			{
				AuthorizationLevel oldLevel = authzMap.get(account);
				if (oldLevel == null || !oldLevel.implies(level))
				{
					authzMap.put(account, level);
				}
			}
		}

		public void AuthorizableMixin.revoke(AuthorizationLevel level,
				Account... accounts)
		{
			Assert.noNullElements(new Object[] { level, accounts });

			int index = level.ordinal();
			if (index == 0)
			{
				for (Account account : accounts)
				{
					authzMap.remove(account);
				}
			}
			else
			{
				AuthorizationLevel threshold = AuthorizationLevel.values()[index - 1];
				for (Account account : accounts)
				{
					AuthorizationLevel oldLevel = authzMap.get(account);
					if (oldLevel != null && !threshold.implies(oldLevel))
					{
						authzMap.put(account,threshold);
					}
				}
			}
		}
	}
}
