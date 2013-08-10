package com.bleulace.domain.crm.command;

import org.apache.shiro.SecurityUtils;
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
import com.bleulace.domain.crm.model.Comment;
import com.bleulace.utils.Locator;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class CommentCommandTest implements CommandGatewayAware
{
	@Autowired
	private CreateAccountCommand cac;

	@Before
	public void beforeMethod()
	{
		sendAndWait(cac);
		SecurityUtils.getSubject().login(cac.getUsername(), cac.getPassword());
	}

	@Test
	public void testCreateComment()
	{
		CommentCommand c = new CommentCommand(Locator.locate(Account.class)
				.getId());
		String content = "Lorem ipsum dolor.";
		c.setContent(content);
		sendAndWait(c);
		Assert.assertTrue(Locator.locate(Account.class)
				.getChildren(Comment.class).get(0).getContent().equals(content));
	}
}