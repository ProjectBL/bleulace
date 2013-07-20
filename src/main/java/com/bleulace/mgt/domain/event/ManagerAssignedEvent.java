package com.bleulace.mgt.domain.event;

import com.bleulace.mgt.domain.ManagementAssignment;

public class ManagerAssignedEvent extends AssignmentEvent<ManagementAssignment>
{
	private static final long serialVersionUID = 397598982306509754L;

	public ManagerAssignedEvent()
	{
	}

	public ManagerAssignedEvent(String id, String accountId,
			ManagementAssignment level)
	{
		super(id, accountId, level);
	}
}