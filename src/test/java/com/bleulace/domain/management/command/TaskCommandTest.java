package com.bleulace.domain.management.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.management.model.ProgressValue;
import com.bleulace.domain.management.model.Task;
import com.bleulace.utils.Locator;

public class TaskCommandTest extends BundleCommandTest implements
		IntegrationTest
{
	@Autowired
	private ManagementCommandFactory factory;

	@Before
	public void createTask()
	{
		factory.createTask(getBundle().getId());
	}

	@Test
	public void createdTaskExistsInDb()
	{
		Assert.assertNotNull(getTask());
		Assert.assertNotNull(getTask().getRoot());
	}

	@Test
	public void testMarkTaskCommand() throws Exception
	{
		sendAndWait(new MarkTaskCommand(getTaskId(), true));
		Assert.assertTrue(getTask().isComplete());
		Assert.assertEquals(new ProgressValue(1, 1), getTask().getProgress());
	}

	public String getTaskId()
	{
		return getTask().getId();
	}

	private Task getTask()
	{
		return Locator.locate(Task.class);
	}
}