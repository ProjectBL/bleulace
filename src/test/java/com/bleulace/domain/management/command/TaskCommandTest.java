package com.bleulace.domain.management.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.management.model.ProgressValue;
import com.bleulace.domain.management.model.Task;
import com.bleulace.utils.Locator;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class TaskCommandTest extends BundleCommandTest
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
	}

	@Test
	public void testMarkTaskCommand() throws Exception
	{
		sendAndWait(new MarkTaskCommand(getTaskId(), true));
		Assert.assertTrue(getTask().isComplete());
		Assert.assertEquals(getTask().getProgress(), new ProgressValue(1, 1));
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