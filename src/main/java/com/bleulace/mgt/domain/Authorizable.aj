package com.bleulace.mgt.domain;

import com.bleulace.crm.domain.Account;

public interface Authorizable
{
	public String getId();
	
	public void grant(AuthorizationLevel level, Account... account);

	public void revoke(AuthorizationLevel level, Account... account);

	public Authorizable getParent();
}