package com.bleulace.mgt.domain;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface EventInvitationDAO extends ReadOnlyDAO<EventInvitation, Long>
{
	@Query("SELECT COUNT(i) FROM EventInvitation i "
			+ "WHERE i.account.id=:accountId "
			+ "AND i.event.window.end > :start AND i.event.window.start < :end")
	public long countByAttendeeAndDates(@Param("accountId") String accountId,
			@Param("start") Date start, @Param("end") Date end);
}