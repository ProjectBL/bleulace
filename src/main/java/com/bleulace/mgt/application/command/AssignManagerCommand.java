package com.bleulace.mgt.application.command;

import com.bleulace.mgt.domain.ManagementAssignment;

public class AssignManagerCommand extends
		AssignmentCommand<ManagementAssignment>
{
	public AssignManagerCommand(String id, String accountId,
			ManagementAssignment role)
	{
		super(id, accountId, role);
	}
}