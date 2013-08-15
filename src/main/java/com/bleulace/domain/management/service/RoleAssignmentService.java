package com.bleulace.domain.management.service;

import java.util.Map;

import com.bleulace.domain.management.model.ManagementLevel;

public interface RoleAssignmentService
{
	public Map<String, ManagementLevel> findManagers(String resourceId);
}