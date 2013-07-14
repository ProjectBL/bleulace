package com.bleulace.mgt.domain;

import java.util.Map;

import com.bleulace.crm.domain.Account;

public interface Manageable
{
	public String getId();

	public Map<Account, ManagementLevel> getManagers();
}