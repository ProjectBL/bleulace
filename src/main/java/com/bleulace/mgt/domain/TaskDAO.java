package com.bleulace.mgt.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface TaskDAO extends ReadOnlyDAO<Task, String>
{
	@Query("SELECT t FROM Task t JOIN t.assignees a WHERE a.id=:accountId")
	public List<Task> findByAccountId(@Param("accountId") String accountId);
}
