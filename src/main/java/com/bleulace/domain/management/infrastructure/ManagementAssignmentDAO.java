package com.bleulace.domain.management.infrastructure;

import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.jpa.ReadOnlyDAO;

public interface ManagementAssignmentDAO extends
		ReadOnlyDAO<ManagementAssignment>, ManagementAssignmentDAOCustom
{
}