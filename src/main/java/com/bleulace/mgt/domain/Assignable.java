package com.bleulace.mgt.domain;

import java.util.Set;

import com.bleulace.crm.domain.Account;

public interface Assignable
{
	public Set<Account> getAssignees();
}