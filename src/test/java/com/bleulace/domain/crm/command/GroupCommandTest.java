package com.bleulace.domain.crm.command;

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
import com.bleulace.domain.AuthenticatingTest;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.AccountGroup;
import com.bleulace.domain.crm.model.GroupMembershipAction;
import com.bleulace.utils.Locator;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class GroupCommandTest extends AuthenticatingTest implements
		CommandGatewayAware
{
	@Autowired
	private CrmCommandFactory factory;

	@Autowired
	private AccountDAO dao;

	@Before
	public void createGroup()
	{
		factory.createGroup();
	}

	@Test
	public void createdGroupExistsInDb()
	{
		assertGroupExists(true);
	}

	@Test
	public void groupCreatorIsAddedAsMember()
	{
		getGroup().getMembers().contains(getAccount());
	}

	@Test
	public void groupNotDeletedWhenNonFinalMemberLeaves()
	{
		joinGroup();
		leaveGroup();
		assertGroupExists(true);
	}

	@Test
	public void groupDeletedWhenFinalMemberLeaves()
	{
		leaveGroup();
		assertGroupExists(false);
	}

	private void assertGroupExists(boolean exists)
	{
		Assert.assertTrue((getGroup() != null) == exists);
	}

	private void joinGroup()
	{
		Account other = dao.findByUsername(factory.createAccount()
				.getUsername());

		GroupMembershipCommand command = makeCommand(GroupMembershipAction.JOIN);
		command.getAccountIds().add(other.getId());
		sendAndWait(command);

		Assert.assertTrue(Locator.locate(AccountGroup.class).getMembers()
				.contains(other));
	}

	private void leaveGroup()
	{
		GroupMembershipCommand command = makeCommand(GroupMembershipAction.LEAVE);
		command.getAccountIds().add(getAccount().getId());
		sendAndWait(command);
	}

	private GroupMembershipCommand makeCommand(GroupMembershipAction action)
	{
		return new GroupMembershipCommand(Locator.locate(AccountGroup.class)
				.getId(), action);
	}

	public AccountGroup getGroup()
	{
		return Locator.locate(AccountGroup.class);
	}
}