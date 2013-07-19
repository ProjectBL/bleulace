package com.bleulace.mgt.application.command;

import com.bleulace.mgt.domain.EventAssignment;

public class AssignEventCommand extends AssignmentCommand<EventAssignment>
{
	public AssignEventCommand(String id, String accountId, EventAssignment role)
	{
		super(id, accountId, role);
	}
}
