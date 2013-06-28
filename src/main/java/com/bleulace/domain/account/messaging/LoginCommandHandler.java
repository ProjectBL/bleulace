package com.bleulace.domain.account.messaging;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

import com.bleulace.domain.account.Account;
import com.frugalu.api.messaging.annotation.Publish;
import com.frugalu.api.messaging.annotation.RegisterBus;
import com.google.common.eventbus.Subscribe;

@Component
@RegisterBus
public class LoginCommandHandler
{
	@Subscribe
	public Boolean handle(LoginCommand command)
	{
		Boolean success = true;
		String username = command.getUsername();
		try
		{
			Account.login(username, command.getPassword());
		}
		catch (AuthenticationException e)
		{
			success = false;
		}
		track(username, success);
		return success;
	}

	@Publish
	private LoginAttemptedEvent track(String username, Boolean success)
	{
		return new LoginAttemptedEvent(username, success);
	}
}
