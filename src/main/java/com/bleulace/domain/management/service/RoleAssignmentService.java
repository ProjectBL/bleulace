package com.bleulace.domain.management.service;

import java.util.Map;

import com.bleulace.domain.management.model.ManagementRole;

public interface RoleAssignmentService
{
	public Map<String, ManagementRole> findManagers(String resourceId);
}