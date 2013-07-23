package com.bleulace.mgt.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface EventDAO extends EventDAOCustom, ReadOnlyDAO<Event, String>
{
	@Query("SELECT e FROM Event e JOIN e.attendees a WHERE a.id=:accountId")
	public List<Event> findByAttendee(@Param("accountId") String accountId);

	@Query("SELECT COUNT(e) FROM Event e JOIN e.attendees a "
			+ "WHERE a.id=:accountId AND e.window.start < :end AND e.window.end > :start")
	public long countByAttendeeAndDates(@Param("accountId") String accountId,
			@Param("start") Date start, @Param("end") Date end);

	@Query("SELECT e FROM Event e JOIN e.attendees a "
			+ "WHERE a.id=:accountId AND e.window.start < :end AND e.window.end > :start")
	public List<Event> findByByAttendeeAndDates(
			@Param("accountId") String accountId, @Param("start") Date start,
			@Param("end") Date end);
}