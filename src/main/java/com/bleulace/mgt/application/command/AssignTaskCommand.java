package com.bleulace.mgt.application.command;

import com.bleulace.mgt.domain.TaskAssignment;

public class AssignTaskCommand extends AssignmentCommand<TaskAssignment>
{
	public AssignTaskCommand(String id, String accountId, TaskAssignment role)
	{
		super(id, accountId, role);
	}
}