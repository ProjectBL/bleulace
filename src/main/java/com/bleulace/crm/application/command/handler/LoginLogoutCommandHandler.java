package com.bleulace.crm.application.command.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.axonframework.domain.GenericEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.cqrs.event.EventBusAware;
import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.crm.application.command.LogoutCommand;
import com.bleulace.crm.application.event.LoginAttemptedEvent;
import com.bleulace.crm.domain.Account;
import com.bleulace.crm.domain.AccountDAO;

/**
 * Handles {@link LoginCommand}s and {@link LogoutCommand}s.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
public class LoginLogoutCommandHandler implements CommandGatewayAware,
		EventBusAware
{
	@Autowired
	private AccountDAO dao;

	public Boolean handle(LoginCommand command)
	{
		Boolean success = true;
		try
		{
			SecurityUtils.getSubject().login(command.getToken());
		}
		catch (AuthenticationException e)
		{
			// the login is NOT a success if an exception is thrown.
			success = false;
		}
		finally
		{
			Account account = dao.findByEmail(command.getUsername());

			// if a valid account email was input,
			if (account != null)
			{
				// track the attempt and whether or not it was a successful
				account.handleLoginAttempt(success);
			}
			eventBus().publish(
					GenericEventMessage.asEventMessage(new LoginAttemptedEvent(
							command.getUsername(), success)));
		}

		// return true if the login attempt was a success, false otherwise
		return success;
	}

	public void handle(LogoutCommand command)
	{
		Account account = SecurityUtils.getAccount();

		// if the executing subject is authenticated,
		if (account != null)
		{
			// unauthenticate him/her
			account.handleLogout();
		}
		// tell shiro we're logging out
		SecurityUtils.getSubject().logout();
	}
}