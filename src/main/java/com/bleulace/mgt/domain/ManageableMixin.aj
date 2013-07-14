package com.bleulace.mgt.domain;

import java.util.HashMap;
import java.util.Map;

import com.bleulace.crm.domain.Account;

public interface ManageableMixin extends Manageable
{
	static aspect Impl
	{
		public Map<Account, ManagementLevel> ManageableMixin.getManagers()
		{
			// TODO : implement me!
			Map<Account, ManagementLevel> managers = new HashMap<Account, ManagementLevel>();
			return managers;
		}
	}
}