package net.bluelace.domain.project;

import junit.framework.Assert;
import net.bluelace.domain.Command;
import net.bluelace.domain.account.Account;
import net.bluelace.domain.account.AccountRegistrationPayload;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext.xml" })
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountTest
{
	@Test
	public void testRegisterAccount() throws Exception
	{
		AccountRegistrationPayload payload = new AccountRegistrationPayload();
		String email = "foo@bar.com";
		payload.setEmail(email);
		payload.setFirstName("foo");
		payload.setLastName("bar");
		new Command(payload).withoutCallback().onAggregate(Account.class, null)
				.send();
		Thread.sleep(100);
		Assert.assertNotNull(Account.findByEmail(email));
	}
}
