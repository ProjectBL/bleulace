package com.bleulace.domain.crm.command;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bleulace.domain.AuthenticationTest;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.FriendRequestAction;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
public class FriendCommandTest extends AuthenticationTest
{
	@Autowired
	private CommandGateway gate;

	@Autowired
	private CreateAccountCommand createAccountCommand;

	@Autowired
	private AccountDAO dao;

	@BeforeClass
	public void createSecondAccount()
	{
		gate.sendAndWait(createAccountCommand);
	}

	@Test
	public void addFriend()
	{
		gate.sendAndWait(new FriendRequestCommand(subjectId(), targetAccount()
				.getId(), FriendRequestAction.REQUEST));

		gate.sendAndWait(new FriendRequestCommand(targetAccount().getId(),
				subjectId(), FriendRequestAction.ACCEPT));

		assert executingAccount().getFriends().size() > 0;

		gate.sendAndWait(new FriendRequestCommand(subjectId(), targetAccount()
				.getId(), FriendRequestAction.REMOVE));

		assert executingAccount().getFriends().size() == 0;
	}

	private Account executingAccount()
	{
		return dao.findOne(subjectId());
	}

	private Account targetAccount()
	{
		return dao.findByUsername(createAccountCommand.getUsername());
	}
}