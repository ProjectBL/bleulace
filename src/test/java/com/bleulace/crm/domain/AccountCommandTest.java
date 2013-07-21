package com.bleulace.crm.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.ChangePasswordCommand;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.application.command.LoginCommand;
import com.bleulace.crm.application.command.ReplyToFriendRequestCommand;
import com.bleulace.crm.application.command.SendFriendRequestCommand;
import com.bleulace.feed.FeedEntry;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountCommandTest implements CommandGatewayAware
{
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private AccountDAO dao;

	@Autowired
	private CreateAccountCommand command;

	@Test
	public void testCreateAccountCommand() throws InterruptedException
	{
		long count = dao.count();
		gateway().send(command);
		Assert.assertEquals(count + 1, dao.count());
		Assert.assertTrue(0 < FeedEntry.findByAccountId(
				dao.findAll().get(0).getId()).size());
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
		Account a = dao.findAll().iterator().next();
		gateway().send(new ChangePasswordCommand(a.getId(), newPassword));
		Assert.assertTrue(gateway().sendAndWait(
				new LoginCommand(a.getEmail(), newPassword)));
	}

	@Test
	public void testRequestFriendCommand() throws Exception
	{
		CreateAccountCommand createInitiator = ctx
				.getBean(CreateAccountCommand.class);
		CreateAccountCommand createRecipient = ctx
				.getBean(CreateAccountCommand.class);

		gateway().send(createInitiator);
		gateway().send(createRecipient);

		List<Account> accs = dao.findAll();

		String initiatorId = accs.get(0).getId();
		String recipientId = accs.get(1).getId();

		SendFriendRequestCommand command = new SendFriendRequestCommand(
				initiatorId, recipientId);

		gateway().send(command);

		ReplyToFriendRequestCommand reply = new ReplyToFriendRequestCommand(
				initiatorId, recipientId);
		reply.setAccepted(true);

		gateway().send(reply);

		Thread.sleep(250);

		Account initiator = em.getReference(Account.class, initiatorId);
		Account recipient = em.getReference(Account.class, recipientId);

		Assert.assertTrue(initiator.getFriends().contains(recipient));
		Assert.assertTrue(recipient.getFriends().contains(initiator));
	}
}