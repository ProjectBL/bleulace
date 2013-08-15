package com.bleulace.domain.management.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.AuthenticatingTest;
import com.bleulace.domain.management.model.Project;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.Locator;

public abstract class ProjectCommandTest extends AuthenticatingTest implements
		CommandGatewayAware
{
	@Autowired
	private ManagementCommandFactory factory;

	private String id;

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
		if (id == null)
		{
			id = Locator.locate(Project.class).getId();
			return getProject();
		}
		return EntityManagerReference.load(Project.class, id);
	}
}