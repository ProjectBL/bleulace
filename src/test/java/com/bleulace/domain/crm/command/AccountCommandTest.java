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
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.Locator;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class AccountCommandTest implements CommandGatewayAware
{
	@Autowired
	private CrmCommandFactory factory;

	@Before
	public void createAccount()
	{
		factory.createAccount();
	}

	@Test
	public void testCreateAccountCommand()
	{
		Assert.assertNotNull(getAccount());
	}

	@Test
	public void testUpateInfoCommand()
	{
		String id = getAccount().getId();
		EditInfoCommand com = new EditInfoCommand(id);
		String name = "baz";
		com.setFirstName(name);
		sendAndWait(com);
		Assert.assertTrue(getAccount().getContactInfo().getFirstName()
				.equals(name));

		Assert.assertTrue(new EditInfoCommand(id).getFirstName().equals(name));
	}

	public Account getAccount()
	{
		return Locator.locate(Account.class);
	}
}
