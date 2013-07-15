package com.bleulace.mgt.domain;

import junit.framework.Assert;

import org.axonframework.domain.IdentifierFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.utils.EntityManagerReference;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectCommandTest implements CommandGatewayAware
{
	@Autowired
	@Qualifier("createAccountCommands")
	private Iterable<CreateAccountCommand> createAccountCommands;

	private final JpaRepository<Project, String> finder = new SimpleJpaRepository<Project, String>(
			Project.class, EntityManagerReference.get());

	private Account account;

	@Before
	public void before()
	{
		CreateAccountCommand command = createAccountCommands.iterator().next();
		gateway().send(command);
		account = EntityManagerReference.get().getReference(Account.class,
				command.getId());
	}

	@Test
	public void testCreateProjectCommand()
	{
		long count = finder.count();
		String id = IdentifierFactory.getInstance().generateIdentifier();
		CreateProjectCommand command = new CreateProjectCommand(id,
				account.getId());
		command.setTitle("Project Name");
		gateway().send(command);
		Assert.assertEquals(count + 1, finder.count());
	}
}