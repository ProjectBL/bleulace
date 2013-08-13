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

import com.bleulace.domain.crm.model.Comment;
import com.bleulace.domain.management.command.BundleCommandTest;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class CommentCommandTest extends BundleCommandTest
{
	@Autowired
	private CrmCommandFactory factory;

	@Test
	public void testCreateComment()
	{
		String content = factory.createComment(getBundle().getId())
				.getContent();
		Assert.assertTrue(getBundle().getChildren(Comment.class).get(0)
				.getContent().equals(content));
	}
}