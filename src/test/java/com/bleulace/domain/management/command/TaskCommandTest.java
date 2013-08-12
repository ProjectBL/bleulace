package com.bleulace.domain.management.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.model.ProgressValue;
import com.bleulace.domain.management.model.Task;
import com.bleulace.utils.Locator;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class TaskCommandTest implements CommandGatewayAware
{
	@Autowired
	@Qualifier("createTaskCommand")
	private CreateTaskCommand command;

	@Test
	public void testCreateTaskCommand() throws Exception
	{
		sendAndWait(command);
		Assert.assertTrue(Locator.exists(Task.class));
	}

	@Test
	public void testMarkTaskCommand() throws Exception
	{
		sendAndWait(command);
		sendAndWait(new MarkTaskCommand(Locator.locate(Task.class).getId(),
				true));
		Assert.assertTrue(Locator.locate(Task.class).isComplete());
		Assert.assertEquals(Locator.locate(Task.class).getProgress(),
				new ProgressValue(1, 1));
	}
}