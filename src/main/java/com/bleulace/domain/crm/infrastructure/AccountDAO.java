package com.bleulace.domain.crm.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface AccountDAO extends ReadOnlyDAO<Account>
{
	public Account findByUsername(String username);

	@Query("SELECT a FROM Account a INNER JOIN a.friends f WHERE f.id=:id")
	public List<String> findFriendIds(@Param("id") String id);
}