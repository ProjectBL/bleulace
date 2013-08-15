package com.bleulace.domain.management.command;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import com.bleulace.cqrs.Send;

@Component
@Send
@ActiveProfiles("test")
public class ManagementCommandFactory
{
	public CreateProjectCommand createProject()
	{
		CreateProjectCommand c = new CreateProjectCommand();
		c.setTitle(randomString());
		return c;
	}

	public CreateBundleCommand createBundle(String parentId)
	{
		CreateBundleCommand c = new CreateBundleCommand(parentId);
		c.setTitle(randomString());
		return c;
	}

	public CreateTaskCommand createTask(String parentId)
	{
		CreateTaskCommand c = new CreateTaskCommand(parentId);
		c.setTitle(randomString());
		return c;
	}

	public CreateEventCommand createEvent()
	{
		CreateEventCommand c = new CreateEventCommand();
		c.setTitle(randomString());
		return c;
	}

	private String randomString()
	{
		return RandomStringUtils.randomAlphabetic(20);
	}
}