package com.bleulace.domain.crm.command;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.crm.model.Comment;
import com.bleulace.domain.management.command.BundleCommandTest;

public class CommentCommandTest extends BundleCommandTest implements
		IntegrationTest
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