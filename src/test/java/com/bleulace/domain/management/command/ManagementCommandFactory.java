package com.bleulace.domain.management.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import com.bleulace.cqrs.Send;
import com.bleulace.domain.management.model.Bundle;
import com.bleulace.domain.management.model.Project;
import com.bleulace.domain.management.model.Task;
import com.bleulace.utils.Locator;

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

	public Map<Class<?>, String> initializeDefaultGroup()
	{
		Map<Class<?>, String> ids = new HashMap<Class<?>, String>();

		createProject();
		ids.put(Project.class, Locator.locate(Project.class).getId());

		createBundle(ids.get(Project.class));
		ids.put(Bundle.class, Locator.locate(Bundle.class).getId());

		createTask(ids.get(Bundle.class));
		ids.put(Task.class, Locator.locate(Task.class).getId());

		return ids;
	}

	private String randomString()
	{
		return RandomStringUtils.randomAlphabetic(20);
	}
}