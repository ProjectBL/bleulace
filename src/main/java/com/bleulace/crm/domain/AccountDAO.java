package com.bleulace.crm.domain;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface AccountDAO extends ReadOnlyDAO<Account, String>,
		AccountDAOCustom
{
}