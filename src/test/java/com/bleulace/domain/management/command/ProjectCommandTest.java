package com.bleulace.domain.management.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.AuthenticatingTest;
import com.bleulace.domain.management.model.Project;
import com.bleulace.utils.Locator;

public abstract class ProjectCommandTest extends AuthenticatingTest implements
		CommandGatewayAware
{
	@Autowired
	private ManagementCommandFactory factory;

	@Before
	public void createProject()
	{
		factory.createProject();
	}

	@Test
	public void createdProjectExistsInDb()
	{
		Assert.assertNotNull(getProject());
	}

	public Project getProject()
	{
		return Locator.locate(Project.class);
	}
}