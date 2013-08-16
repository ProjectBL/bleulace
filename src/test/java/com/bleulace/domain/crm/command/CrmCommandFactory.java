package com.bleulace.domain.crm.command;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.Send;
import com.bleulace.domain.crm.model.ContactInformation;

@Send
@Component
public class CrmCommandFactory
{
	public static final String ACCOUNT_PASSWORD = "password";

	private static final ContactInformation INFO = new ContactInformation(
			"Arleigh", "Dickerson", "arleighdickerson@frugalu.com", "Marshall",
			"Work my ass off");

	public ContactInformation getContactInformation()
	{
		return INFO;
	}

	public CreateAccountCommand createAccount()
	{
		return new CreateAccountCommand(randomString(), ACCOUNT_PASSWORD,
				getContactInformation());
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
