package com.bleulace.domain.management.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bleulace.domain.management.model.PersistentEvent;

public interface EventDAO extends JpaRepository<PersistentEvent,String>, EventDAOCustom
{
}