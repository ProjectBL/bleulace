package com.bleulace.mgt.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface EventDAO extends EventDAOCustom, ReadOnlyDAO<Event, String>
{
	@Query("SELECT e FROM Event e JOIN e.attendees a WHERE a.id=:accountId")
	public List<Event> findByAttendee(@Param("accountId") String accountId);
}