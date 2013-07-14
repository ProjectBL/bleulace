package com.bleulace.crm.domain;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountCommandTest implements CommandGatewayAware
{
	@Autowired
	private AccountFinder finder;

	@Autowired
	@Qualifier("createAccountCommands")
	private Iterable<CreateAccountCommand> createAccountCommands;

	@Test
	public void testCreateAccountCommand() throws InterruptedException
	{
		long count = finder.count();
		CreateAccountCommand command = createAccountCommands.iterator().next();
		gateway().send(command);
		Thread.sleep(100);
		Assert.assertEquals(count + 1, finder.count());
	}
}