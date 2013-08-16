package com.bleulace.domain.management.infrastructure;

import com.bleulace.domain.management.model.Event;
import com.bleulace.jpa.ReadOnlyDAO;

public interface EventDAO extends ReadOnlyDAO<Event>, EventDAOCustom
{
}