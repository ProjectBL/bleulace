package com.bleulace.mgt.application.command;

import com.bleulace.crm.application.command.CreateGroupCommand;
import com.bleulace.crm.application.command.JoinGroupCommand;

public interface MgtCommandGateway
{
	public void send(CreateProjectCommand command);

	public void send(AddBundleCommand command);

	public void send(AddTaskCommand command);

	public void send(CreateGroupCommand command);

	public void send(JoinGroupCommand command);

	public void send(AddCommentCommand command);

	public void send(AssignTaskCommand command);

	public void send(CreateEventCommand command);
}