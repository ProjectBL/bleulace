package com.bleulace.domain;

import org.apache.shiro.SecurityUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.ctx.SpringApplicationContext;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public abstract class AuthenticatingTest
{
	@Autowired
	private AccountDAO dao;

	private String username;

	@Before
	public void beforeMethod()
	{
		CreateAccountCommand command = SpringApplicationContext
				.getBean(CreateAccountCommand.class);
		SpringApplicationContext.getBean(CommandGateway.class).sendAndWait(
				command);
		username = command.getUsername();
		SecurityUtils.getSubject().login(username, command.getPassword());
	}

	@After
	public void afterMethod()
	{
		SecurityUtils.getSubject().logout();
	}

	public Account executing()
	{
		return dao.findByUsername(username);
	}
}