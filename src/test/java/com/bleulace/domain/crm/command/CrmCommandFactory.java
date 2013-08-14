package com.bleulace.domain.crm.command;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.Send;

@Send
@Component
public class CrmCommandFactory
{
	public static final String ACCOUNT_PASSWORD = "password";

	public CreateAccountCommand createAccount()
	{
		return new CreateAccountCommand(randomString(), ACCOUNT_PASSWORD);
	}

	public CommentCommand createComment(String parentId)
	{
		return new CommentCommand(parentId, randomString());
	}

	private String randomString()
	{
		return RandomStringUtils.randomAlphabetic(20);
	}

	public CreateGroupCommand createGroup()
	{
		CreateGroupCommand c = new CreateGroupCommand();
		c.setTitle(randomString());
		return c;
	}
}
