package com.bleulace.domain.management.infrastructure;

import com.bleulace.domain.management.model.ManagementAssignment;

interface ManagementAssignmentDAOCustom
{
	ManagementAssignment findAssignment(String resourceId, String accountId);
}