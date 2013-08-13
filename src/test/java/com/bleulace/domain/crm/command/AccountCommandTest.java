package com.bleulace.domain.crm.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.Locator;

public class AccountCommandTest implements IntegrationTest, CommandGatewayAware
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
