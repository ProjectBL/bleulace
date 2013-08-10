package com.bleulace.domain.crm.command;

import java.util.Iterator;

import org.apache.shiro.SecurityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.FriendRequestAction;
import com.bleulace.utils.Locator;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.utils.jpa.EntityManagerReference;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class FriendCommandTest implements CommandGatewayAware
{
	@Autowired
	private AccountDAO dao;

	private String initiatorId;
	private String recipientId;

	@Before
	public void beforeMethod()
	{
		for (int i = 0; i < 2; i++)
		{
			CreateAccountCommand command = SpringApplicationContext
					.getBean(CreateAccountCommand.class);
			sendAndWait(command);
			if (i == 0)
			{
				SecurityUtils.getSubject().login(command.getUsername(),
						command.getPassword());
			}
		}

		Iterator<String> it = Locator.ids(Account.class).iterator();
		initiatorId = it.next();
		recipientId = it.next();
	}

	@Test
	public void testFriendAccepted()
	{
		doActionsInSequence(FriendRequestAction.REQUEST,
				FriendRequestAction.ACCEPT);
		Assert.assertTrue(areFriends());
	}

	@Test
	public void testFriendDeclined()
	{
		doActionsInSequence(FriendRequestAction.REQUEST,
				FriendRequestAction.DENY);
		Assert.assertFalse(areFriends());
	}

	@Test
	public void testFriendCanceled()
	{
		sendAndWait(new FriendRequestCommand(initiatorId, recipientId,
				FriendRequestAction.REQUEST));
		sendAndWait(new FriendRequestCommand(initiatorId, recipientId,
				FriendRequestAction.CANCEL));
		Assert.assertFalse(areFriends());
	}

	@Test
	public void testFriendRemoved()
	{
		testFriendAccepted();
		sendAndWait(new FriendRequestCommand(initiatorId, recipientId,
				FriendRequestAction.REMOVE));
		Assert.assertFalse(areFriends());
	}

	private void doActionsInSequence(FriendRequestAction cause,
			FriendRequestAction effect)
	{
		sendAndWait(new FriendRequestCommand(initiatorId, recipientId, cause));
		sendAndWait(new FriendRequestCommand(recipientId, initiatorId, effect));
	}

	private boolean areFriends()
	{
		return recipient().getFriends().contains(initiator())
				&& initiator().getFriends().contains(recipient());
	}

	private Account initiator()
	{
		return loadAccount(initiatorId);
	}

	private Account recipient()
	{
		return loadAccount(recipientId);
	}

	private Account loadAccount(String id)
	{
		return EntityManagerReference.load(Account.class, id);
	}
}