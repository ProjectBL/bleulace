package net.bluelace.domain.project.account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.bluelace.domain.account.Account;
import net.bluelace.domain.project.Fixtures;

import org.junit.Assert;
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
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void testLogin()
	{
		String password = "password";
		Account a = new Fixtures().getAccount();
		a.setPassword(password);
		entityManager.persist(a);
		Account.login(a.getEmail(), password);
		Assert.assertEquals(a, Account.current());
	}
}