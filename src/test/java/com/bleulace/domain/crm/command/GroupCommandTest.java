package com.bleulace.domain.crm.command;

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
import com.bleulace.domain.crm.model.AccountGroup;
import com.bleulace.domain.crm.model.GroupMembershipAction;
import com.bleulace.utils.Locator;
import com.bleulace.utils.ctx.SpringApplicationContext;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class GroupCommandTest implements CommandGatewayAware
{
	@Autowired
	private AccountDAO dao;

	@Autowired
	private CreateAccountCommand accountCommand;

	@Autowired
	private CreateGroupCommand groupCommand;

	@Before
	public void beforeMethod()
	{
		sendAndWait(accountCommand);
		SecurityUtils.getSubject().login(accountCommand.getUsername(),
				accountCommand.getPassword());

		sendAndWait(groupCommand);
		AccountGroup g = Locator.locate(AccountGroup.class);
		Assert.assertTrue(g.getMembers().contains(executingAccount()));
	}

	@Test
	public void testJoinGroupCommand()
	{
		CreateAccountCommand c = SpringApplicationContext
				.getBean(CreateAccountCommand.class);
		sendAndWait(c);
		Account other = dao.findByUsername(c.getUsername());

		GroupMembershipCommand command = makeCommand(GroupMembershipAction.JOIN);
		command.getAccountIds().add(other.getId());
		sendAndWait(command);

		Assert.assertTrue(Locator.locate(AccountGroup.class).getMembers()
				.contains(other));
	}

	@Test
	public void testLeaveGroupCommand()
	{
		GroupMembershipCommand command = makeCommand(GroupMembershipAction.LEAVE);
		command.getAccountIds().add(executingAccount().getId());
		sendAndWait(command);
		Assert.assertFalse(Locator.locate(AccountGroup.class).getMembers()
				.contains(executingAccount()));
	}

	private GroupMembershipCommand makeCommand(GroupMembershipAction action)
	{
		return new GroupMembershipCommand(Locator.locate(AccountGroup.class)
				.getId(), action);
	}

	private Account executingAccount()
	{
		return dao.findByUsername(accountCommand.getUsername());
	}
}