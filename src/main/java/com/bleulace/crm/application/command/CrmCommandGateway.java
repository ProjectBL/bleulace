package com.bleulace.crm.application.command;


public interface CrmCommandGateway
{
	public boolean sendAndWait(LoginCommand command);

	public void send(LogoutCommand command);

	public void send(ChangePasswordCommand command);

	public void send(CreateAccountCommand command);
}