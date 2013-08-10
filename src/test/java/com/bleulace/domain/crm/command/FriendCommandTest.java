package com.bleulace.domain.crm.command;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.Locator;
import com.bleulace.utils.ctx.SpringApplicationContext;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class FriendCommandTest implements CommandGatewayAware
{
	private String initiatorId;
	private String recipientId;

	@Before
	public void beforeMethod()
	{
		for (int i = 0; i < 2; i++)
		{
			sendAndWait(SpringApplicationContext
					.getBean(CreateAccountCommand.class));
		}

		Iterator<String> it = Locator.ids(Account.class).iterator();
		initiatorId = it.next();
		recipientId = it.next();
	}

	@Test
	public void testFriendRequested()
	{
	}

	@Test
	public void testFriendAccepted()
	{
	}

	@Test
	public void testFriendDeclined()
	{
	}

	@Test
	public void testFriendCanceled()
	{
	}

	@Test
	public void testFriendRemoved()
	{
	}
}