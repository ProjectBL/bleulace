package com.bleulace.mgt.domain;

import java.util.Date;
import java.util.List;

import com.bleulace.utils.jpa.ReadOnlyDAO;

public interface EventDAO extends ReadOnlyDAO<Event, String>
{
	public List<Event> findBetweenDates(Date start, Date end);
}