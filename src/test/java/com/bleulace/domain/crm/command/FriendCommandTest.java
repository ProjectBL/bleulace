package com.bleulace.domain.crm.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.FriendRequestAction;
import com.bleulace.utils.jpa.EntityManagerReference;

public class FriendCommandTest implements IntegrationTest, CommandGatewayAware
{
	@Autowired
	private AccountDAO accountDAO;

	private AccountManager initiator;
	private AccountManager recipient;

	@Before
	public void setUp()
	{
		initiator = new AccountManager();
		recipient = new AccountManager();
	}

	@Test
	public void testFriendRequested()
	{
		sendCommand(initiator, FriendRequestAction.REQUEST);
		Assert.assertTrue(hasRequest(initiator, recipient));
	}

	@Test
	public void testFriendAccepted()
	{
		sendCommand(initiator, FriendRequestAction.REQUEST);
		sendCommand(recipient, FriendRequestAction.ACCEPT);
		Assert.assertFalse(hasRequest(initiator, recipient));
		Assert.assertTrue(areFriends(initiator, recipient));
	}

	@Test
	public void testFriendDeclined()
	{
		sendCommand(initiator, FriendRequestAction.REQUEST);
		sendCommand(recipient, FriendRequestAction.DENY);
		Assert.assertFalse(hasRequest(initiator, recipient));
		Assert.assertFalse(areFriends(initiator, recipient));
	}

	@Test
	public void testFriendRequestCanceled()
	{
		sendCommand(initiator, FriendRequestAction.REQUEST);
		sendCommand(initiator, FriendRequestAction.CANCEL);
		Assert.assertFalse(hasRequest(initiator, recipient));
		Assert.assertFalse(areFriends(initiator, recipient));
	}

	private void sendCommand(AccountManager sender, FriendRequestAction action)
	{
		sender.login();
		String targetId = sender == initiator ? recipient.getId() : initiator
				.getId();
		sendAndWait(new FriendRequestCommand(targetId, action));
	}

	private boolean hasRequest(AccountManager initiator,
			AccountManager recipient)
	{
		return recipient.getAccount().getFriendRequests()
				.containsKey(initiator.getAccount());
	}

	private boolean areFriends(AccountManager initiator,
			AccountManager recipient)
	{
		Account a = initiator.getAccount();
		Account b = recipient.getAccount();
		return a.getFriends().contains(b) && b.getFriends().contains(a);
	}

	private Account loadAccount(String id)
	{
		return EntityManagerReference.load(Account.class, id);
	}
}