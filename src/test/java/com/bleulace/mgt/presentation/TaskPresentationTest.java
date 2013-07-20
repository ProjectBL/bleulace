package com.bleulace.mgt.presentation;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.AddTaskCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskPresentationTest implements CommandGatewayAware
{
	@Autowired
	private AddTaskCommand addTaskCommand;

	@Test
	public void testTaskFinder()
	{
		gateway().send(addTaskCommand);

		List<TaskDTO> dtos = TaskDTO.FINDER.findAll();
		Assert.assertTrue(dtos.size() > 0);
		TaskDTO dto = dtos.get(0);
		Assert.assertNotNull(dto.getId());
		Assert.assertNotNull(dto.getTitle());
	}
}
