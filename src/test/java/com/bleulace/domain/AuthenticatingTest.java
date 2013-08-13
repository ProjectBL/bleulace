package com.bleulace.domain;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.ctx.SpringApplicationContext;

public abstract class AuthenticatingTest
{
	@Autowired
	private AccountDAO dao;

	private String username;

	@Before
	public void doLogin()
	{
		CreateAccountCommand command = SpringApplicationContext
				.getBean(CreateAccountCommand.class);
		SpringApplicationContext.getBean(CommandGateway.class).sendAndWait(
				command);
		username = command.getUsername();
		try
		{
			SecurityUtils.getSubject().login(username, command.getPassword());
		}
		catch (AuthenticationException e)
		{
		}
	}

	@Test
	public void isSubjectAuthenticated()
	{
		Assert.assertTrue(SecurityUtils.getSubject().isAuthenticated());
	}

	@After
	public void doLogout()
	{
		SecurityUtils.getSubject().logout();
	}

	public Account getAccount()
	{
		return dao.findByUsername(username);
	}
}