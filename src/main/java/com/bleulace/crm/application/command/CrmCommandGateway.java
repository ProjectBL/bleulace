package com.bleulace.crm.application.command;

import com.bleulace.mgt.application.command.AssignManagerCommand;

public interface CrmCommandGateway
{
	public boolean sendAndWait(LoginCommand command);

	public void sendAndWait(LogoutCommand command);

	public void send(ChangePasswordCommand command);

	public void send(CreateAccountCommand command);

	public void sendAndWait(CreateAccountCommand command);

	public void send(AssignManagerCommand command);

	public void send(SendFriendRequestCommand command);

	public void send(ReplyToFriendRequestCommand command);

	public void send(CreateGroupCommand command);

	public void send(JoinGroupCommand command);
}