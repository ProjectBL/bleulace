package com.bleulace.crm.domain;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.ChangePasswordCommand;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.application.command.LoginCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountCommandTest implements CommandGatewayAware
{
	@Autowired
	private AccountFinder finder;

	@Autowired
	private CreateAccountCommand command;

	@Test
	public void testCreateAccountCommand() throws InterruptedException
	{
		long count = finder.count();
		gateway().send(command);
		Assert.assertEquals(count + 1, finder.count());
	}

	@Test
	public void testLoginCommand()
	{
		gateway().send(command);
		Assert.assertTrue(gateway().sendAndWait(
				new LoginCommand(command.getEmail(), command.getPassword())));
	}

	@Test
	public void testChangePasswordCommand()
	{
		gateway().send(command);
		String newPassword = "password";
		gateway().send(new ChangePasswordCommand(command.getId(), newPassword));
		Assert.assertTrue(gateway().sendAndWait(
				new LoginCommand(command.getEmail(), newPassword)));

	}
}