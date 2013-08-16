package com.bleulace.domain.crm.infrastructure;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.command.AccountManager;
import com.bleulace.domain.crm.command.FriendRequestCommand;
import com.bleulace.domain.crm.model.FriendRequestAction;

public class AccountDAOTest implements IntegrationTest, CommandGatewayAware
{
	@Autowired
	private AccountDAO dao;

	@Test
	public void testFindFriendRequests()
	{
		new AccountManager().login();
		String targetId = new AccountManager().getId();
		sendAndWait(new FriendRequestCommand(targetId,
				FriendRequestAction.REQUEST));
		Assert.assertEquals(1, dao.findFriendRequests(targetId).size());
	}

	@Test
	public void testFindFriends()
	{
		AccountManager ex = new AccountManager().login();
		AccountManager target = new AccountManager();
		sendAndWait(new FriendRequestCommand(target.getId(),
				FriendRequestAction.REQUEST));
		target.login();
		sendAndWait(new FriendRequestCommand(ex.getId(),
				FriendRequestAction.ACCEPT));

		Assert.assertEquals(1, dao.findFriends(target.getId()).size());
	}

	@Test
	public void testFindFriendIds()
	{
		AccountManager ex = new AccountManager().login();
		AccountManager target = new AccountManager();
		sendAndWait(new FriendRequestCommand(target.getId(),
				FriendRequestAction.REQUEST));
		target.login();
		sendAndWait(new FriendRequestCommand(ex.getId(),
				FriendRequestAction.ACCEPT));

		Assert.assertEquals(1, dao.findFriendIds(target.getId()).size());
	}
}
