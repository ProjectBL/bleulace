package com.bleulace.crm.domain;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateGroupCommand;
import com.bleulace.crm.application.command.JoinGroupCommand;

@Transactional
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupCommandTest implements CommandGatewayAware
{
	@Autowired
	private GroupDAO groupDAO;

	@Autowired
	private CreateGroupCommand createGroupCommand;

	@Autowired
	private JoinGroupCommand joinGroupCommand;

	@Test
	public void testCreateGroupCommand() throws Exception
	{
		gateway().send(createGroupCommand);
		AccountGroup group = groupDAO.findAll().iterator().next();
		Assert.assertNotNull(group);
		Assert.assertTrue(group.getMembers().size() > 0);
	}

	@Test
	public void testJoinGroupCommand()
	{
		int size = groupDAO.findOne(joinGroupCommand.getGroupId()).getMembers()
				.size();
		gateway().send(joinGroupCommand);
		Assert.assertEquals(size + 1,
				groupDAO.findOne(joinGroupCommand.getGroupId()).getMembers()
						.size());
	}
}
