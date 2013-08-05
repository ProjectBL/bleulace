package com.bleulace.mgt.application.command;

import com.bleulace.mgt.domain.SchedulingConflictException;

public interface MgtCommandGateway
{
	public void send(CreateProjectCommand command);

	public void sendAndWait(CreateProjectCommand command);

	public void send(AddBundleCommand command);

	public void send(AddTaskCommand command);

	public void send(AddCommentCommand command);

	public void send(AssignTaskCommand command);

	public void send(CreateEventCommand command);

	public void sendAndWait(CreateEventCommand command);

	public void send(MoveEventCommand command);

	public void send(ResizeEventCommand command);

	public void send(InviteGuestsCommand command);

	public void sendAndWait(InviteGuestsCommand command);

	public void send(RsvpCommand command) throws SchedulingConflictException;
}