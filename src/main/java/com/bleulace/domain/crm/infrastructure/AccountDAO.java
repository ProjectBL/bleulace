package com.bleulace.domain.crm.infrastructure;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.ReadOnlyDAO;

public interface AccountDAO extends AccountDAOCustom, ReadOnlyDAO<Account>
{
	public Account findByUsername(String username);
}