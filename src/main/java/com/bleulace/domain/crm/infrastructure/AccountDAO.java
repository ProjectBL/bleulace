package com.bleulace.domain.crm.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bleulace.domain.crm.model.Account;

public interface AccountDAO extends AccountDAOCustom,
		JpaRepository<Account, String>
{
}