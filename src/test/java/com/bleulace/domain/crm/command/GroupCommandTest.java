package com.bleulace.domain.crm.command;

import org.apache.commons.lang3.RandomStringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bleulace.domain.AuthenticationTest;
import com.bleulace.domain.crm.infrastructure.GroupDAO;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
public class GroupCommandTest extends AuthenticationTest
{
	@Autowired
	private CommandGateway gate;

	@Autowired
	private GroupDAO dao;

	@BeforeMethod
	public void createGroup()
	{
		CreateGroupCommand c = new CreateGroupCommand();
		c.setTitle(RandomStringUtils.random(20));
		gate.sendAndWait(c);
	}

	@Test
	public void isGroupCreated()
	{
		assert dao.count() > 0;
	}

	@Test
	public void isGroupCreatorAddedToGroup()
	{
		assert dao.findAll().get(0).getMembers().size() > 0;
	}
}
