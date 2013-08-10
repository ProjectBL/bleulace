package com.bleulace.domain;

import org.apache.shiro.SecurityUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.infrastructure.AccountDAO;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@TransactionConfiguration
@ActiveProfiles("test")
public abstract class AuthenticationTest extends
		AbstractTestNGSpringContextTests
{
	@Autowired
	private CreateAccountCommand command;

	@Autowired
	private AccountDAO dao;

	@Autowired
	private CommandGateway gate;

	@BeforeClass
	public void logInUser()
	{
		gate.sendAndWait(command);
		SecurityUtils.getSubject().login(command.getUsername(),
				command.getPassword());
	}

	@Test
	public void isUserAuthenticated()
	{
		assert SecurityUtils.getSubject().isAuthenticated();
	}

	@AfterClass
	public void logOutUser()
	{
		SecurityUtils.getSubject().logout();
	}

	protected String subjectId()
	{
		return SecurityUtils.getSubject().getId();
	}
}