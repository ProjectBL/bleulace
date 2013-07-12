package com.bleulace.crm.application.command;

import org.apache.shiro.authc.UsernamePasswordToken;

public interface CrmCommandGateway
{
	public boolean sendAndWait(UsernamePasswordToken command);

	public void send(LogoutCommand command);

	public void send(ChangePasswordCommand command);

	public void send(CreateAccountCommand command);
}