package com.bleulace.domain.resource.infrastructure;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.ReadOnlyDAO;

public interface ResourceDAO extends ReadOnlyDAO<AbstractResource>,
		ResourceDAOCustom
{
}