package com.bleulace.crm.domain;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
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
	@PersistenceContext
	private EntityManager em;

	private JpaRepository<AccountGroup, String> repository;

	@PostConstruct
	protected void init()
	{
		repository = new SimpleJpaRepository<AccountGroup, String>(
				AccountGroup.class, em);
	}

	@Autowired
	private CreateGroupCommand createGroupCommand;

	@Autowired
	private JoinGroupCommand joinGroupCommand;

	@Test
	public void testCreateGroupCommand()
	{
		gateway().send(createGroupCommand);
		Assert.assertNotNull(repository.findOne(createGroupCommand.getId()));
	}

	@Test
	public void testJoinGroupCommand()
	{
		int size = repository.findOne(joinGroupCommand.getGroupId())
				.getMembers().size();
		gateway().send(joinGroupCommand);
		Assert.assertEquals(size + 1,
				repository.findOne(joinGroupCommand.getGroupId()).getMembers()
						.size());
	}
}
