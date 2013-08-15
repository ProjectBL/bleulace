package com.bleulace.domain.resource.model;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.management.command.ManagementCommandFactory;
import com.bleulace.domain.management.model.Bundle;
import com.bleulace.domain.management.model.Project;
import com.bleulace.domain.management.model.Task;
import com.bleulace.utils.Locator;

public class ResourcePermissionTest implements IntegrationTest
{
	@Autowired
	private ManagementCommandFactory factory;

	private List<String> ids;

	private String projectId;
	private String bundleId;
	private String taskId;

	@Before
	public void createResources()
	{
		ids = new LinkedList<String>();

		factory.createProject();
		projectId = Locator.locate(Project.class).getId();
		ids.add(projectId);

		factory.createBundle(projectId);
		bundleId = Locator.locate(Bundle.class).getId();
		ids.add(bundleId);

		factory.createTask(bundleId);
		taskId = Locator.locate(Task.class).getId();
		ids.add(taskId);
	}

	@Test
	public void preConditions()
	{
		Project p = Locator.locate(Project.class);
		Bundle b = Locator.locate(Bundle.class);
		Task t = Locator.locate(Task.class);

		Assert.assertTrue(p.getChildren().contains(b));
		Assert.assertTrue(b.getChildren().contains(t));
	}

	@Test
	public void testProject()
	{
		doAssertion(projectId, projectId, true);
		doAssertion(projectId, bundleId, true);
		doAssertion(projectId, bundleId, true);
	}

	@Test
	public void testBundle()
	{
		doAssertion(bundleId, projectId, false);
		doAssertion(bundleId, bundleId, true);
		doAssertion(bundleId, taskId, true);
	}

	@Test
	public void testTask()
	{
		doAssertion(taskId, projectId, false);
		doAssertion(taskId, bundleId, false);
		doAssertion(taskId, taskId, true);
	}

	@Test
	public void testProve()
	{
		for (String id : ids)
		{
			prove(id);
		}
	}

	private void prove(String referenceId)
	{
		int referencePosition = ids.indexOf(referenceId);
		for (String currentId : ids)
		{
			boolean shouldAuthorize = ids.indexOf(currentId) >= referencePosition;
			doAssertion(referenceId, currentId, shouldAuthorize);
		}
	}

	private void doAssertion(String parent, String child, boolean value)
	{
		String message = "error at PARENT:" + identifyValue(parent) + " CHILD:"
				+ identifyValue(child);
		Assert.assertTrue(message, new ResourcePermission(parent)
				.implies(new ResourcePermission(child)) == value);
	}

	private String identifyValue(String id)
	{
		if (id.equals(projectId))
		{
			return "projectId";
		}
		if (id.equals(bundleId))
		{
			return "bundleId";
		}
		if (id.equals(taskId))
		{
			return "taskId";
		}
		throw new IllegalArgumentException();
	}
}
