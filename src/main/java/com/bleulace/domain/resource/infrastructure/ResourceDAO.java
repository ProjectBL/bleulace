package com.bleulace.domain.resource.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bleulace.domain.resource.model.AbstractResource;

public interface ResourceDAO extends JpaRepository<AbstractResource, String>,
		ResourceDAOCustom
{
}