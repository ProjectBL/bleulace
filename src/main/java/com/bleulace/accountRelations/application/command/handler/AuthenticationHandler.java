package com.bleulace.accountRelations.application.command.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bleulace.accountRelations.application.command.LoginCommand;
import com.bleulace.accountRelations.application.command.LogoutCommand;
import com.bleulace.accountRelations.domain.Account;
import com.bleulace.accountRelations.domain.AccountFinder;
import com.bleulace.accountRelations.infrastructure.ExecutingAccount;
import com.bleulace.accountRelations.infrastructure.LoginCommandConverter;

@Component
public class AuthenticationHandler
{
	@Autowired
	private ExecutingAccount executingAccount;

	@Autowired
	@Qualifier(LoginCommandConverter.NAME)
	private Converter<LoginCommand, AuthenticationToken> converter;

	@Autowired
	private AccountFinder finder;

	@CommandHandler
	public Boolean login(LoginCommand command)
	{
		Boolean success = true;
		try
		{
			SecurityUtils.getSubject().login(converter.convert(command));
		}
		catch (AuthenticationException e)
		{
			success = false;
		}

		Account account = finder.findByEmail(command.getUsername());
		if (account != null)
		{
			account.onLogin(success);
		}
		return success;
	}

	public void logout(LogoutCommand command)
	{
		Account account = executingAccount.current();
		if (account != null)
		{
			account.onLogout();
		}
		SecurityUtils.getSubject().logout();
	}
}