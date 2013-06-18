package net.bluelace.domain.project;

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
	}
}
