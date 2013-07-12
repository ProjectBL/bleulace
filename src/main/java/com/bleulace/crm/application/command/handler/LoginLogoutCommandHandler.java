package com.bleulace.crm.application.command.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.crm.application.command.LogoutCommand;
import com.bleulace.crm.domain.Account;
import com.bleulace.crm.domain.AccountFinder;
import com.bleulace.crm.infrastructure.ExecutingAccount;

/**
 * Handles {@link LoginCommand}s and {@link LogoutCommand}s.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
public class LoginLogoutCommandHandler
{
	@Autowired
	private ExecutingAccount executingAccount;

	@Autowired
	@Qualifier(UsernamePasswordLoginCommandConverter.NAME)
	private LoginCommandConverter converter;

	@Autowired
	private AccountFinder finder;

	@CommandHandler
	public Boolean login(LoginCommand command)
	{
		// is the login a success?
		Boolean success = true;
		try
		{
			SecurityUtils.getSubject().login(converter.convert(command));
		}
		catch (AuthenticationException e)
		{
			// the login is NOT a success if an exception is thrown.
			success = false;
		}

		Account account = finder.findByEmail(command.getUsername());

		// if a valid account email was input,
		if (account != null)
		{
			// track the login attempt and whether or not it was successful
			account.handleLoginAttempt(success);
		}

		// return true if the login attempt was a success, false otherwise
		return success;
	}

	public void logout(LogoutCommand command)
	{
		Account account = executingAccount.current();

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