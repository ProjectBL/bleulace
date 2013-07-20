package com.bleulace.crm.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface GroupDAO extends GroupDAOCustom,
		ReadOnlyDAO<AccountGroup, String>
{
	@Query("SELECT g FROM AccountGroup g JOIN g.members m WHERE m.id=:memberId")
	public List<AccountGroup> findByMemberId(@Param("memberId") String memberId);
}