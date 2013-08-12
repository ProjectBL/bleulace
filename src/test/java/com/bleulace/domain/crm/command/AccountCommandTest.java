package com.bleulace.domain.crm.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.Locator;
import com.bleulace.utils.jpa.EntityManagerReference;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class AccountCommandTest implements CommandGatewayAware
{
	@Autowired
	private CreateAccountCommand command;

	@Test
	public void testCreateAccountCommand()
	{
		sendAndWait(command);
		Assert.assertTrue(Locator.exists(Account.class));
	}

	@Test
	public void testUpateInfoCommand()
	{
		sendAndWait(command);
		String id = Locator.locate(Account.class).getId();
		UpdateContactInfoCommand com = new UpdateContactInfoCommand(id);
		String name = "baz";
		com.setFirstName(name);
		sendAndWait(com);
		String other = EntityManagerReference.load(Account.class, id)
				.getContactInformation().getFirstName();
		System.out.println(other);
		Assert.assertTrue(EntityManagerReference.load(Account.class, id)
				.getContactInformation().getFirstName().equals(name));
	}
}
