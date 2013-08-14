package com.bleulace.domain.crm.command;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;

@Configurable(preConstruction = true)
public class AccountManager implements CommandGatewayAware
{
	@Autowired
	private CrmCommandFactory factory;

	@Autowired
	private AccountDAO dao;

	private final String username;

	public AccountManager()
	{
		username = factory.createAccount().getUsername();
	}

	public Account getAccount()
	{
		return dao.findByUsername(username);
	}

	public void login()
	{
		SecurityUtils.getSubject().login(username,
				CrmCommandFactory.ACCOUNT_PASSWORD);
	}

	public void logout()
	{
		SecurityUtils.getSubject().logout();
	}

	public String getId()
	{
		return getAccount().getId();
	}
}