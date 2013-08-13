package com.bleulace.domain.crm.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.AuthenticatingTest;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.FriendRequestAction;
import com.bleulace.utils.jpa.EntityManagerReference;

public class FriendCommandTest extends AuthenticatingTest implements
		IntegrationTest, CommandGatewayAware
{
	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private CrmCommandFactory factory;

	private String recipientId;

	@Before
	public void createRecipient()
	{
		recipientId = accountDAO.findByUsername(
				factory.createAccount().getUsername()).getId();
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
		sendAndWait(new FriendRequestCommand(initiatorId(), recipientId,
				FriendRequestAction.REQUEST));
		sendAndWait(new FriendRequestCommand(initiatorId(), recipientId,
				FriendRequestAction.CANCEL));
		Assert.assertFalse(areFriends());
	}

	@Test
	public void testFriendRemoved()
	{
		doActionsInSequence(FriendRequestAction.REQUEST,
				FriendRequestAction.ACCEPT);
		sendAndWait(new FriendRequestCommand(initiatorId(), recipientId,
				FriendRequestAction.REMOVE));
		Assert.assertFalse(areFriends());
	}

	private void doActionsInSequence(FriendRequestAction cause,
			FriendRequestAction effect)
	{
		sendAndWait(new FriendRequestCommand(initiatorId(), recipientId, cause));
		sendAndWait(new FriendRequestCommand(recipientId, initiatorId(), effect));
	}

	private boolean areFriends()
	{
		return recipient().getFriends().contains(initiator())
				&& initiator().getFriends().contains(recipient());
	}

	private String initiatorId()
	{
		return getAccount().getId();
	}

	private String recipientId()
	{
		return recipientId;
	}

	private Account initiator()
	{
		return getAccount();
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