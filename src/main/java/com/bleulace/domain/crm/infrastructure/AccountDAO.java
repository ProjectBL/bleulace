package com.bleulace.domain.crm.infrastructure;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface AccountDAO extends ReadOnlyDAO<Account>
{
	public Account findByUsername(String username);
}