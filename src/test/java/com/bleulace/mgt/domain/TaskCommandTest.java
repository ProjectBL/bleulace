package com.bleulace.mgt.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.AssignTaskCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskCommandTest implements CommandGatewayAware
{

	@Autowired
	private AddTaskCommand addTaskCommand;

	@Autowired
	private AssignTaskCommand assignTaskCommand;

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testAddTaskCommand()
	{
		String id = addTaskCommand.getParentId();
		long taskCount = em.getReference(Bundle.class, id).getTasks().size();
		gateway().send(addTaskCommand);
		Assert.assertEquals(taskCount + 1, em.getReference(Bundle.class, id)
				.getTasks().size());
	}

	@Test
	public void testAssignTaskCommand()
	{
		String id = assignTaskCommand.getId();

		int oldCount = em.getReference(Task.class, id).getAssignees().size();
		gateway().send(assignTaskCommand);
		int newCount = em.getReference(Task.class, id).getAssignees().size();

		Assert.assertEquals(oldCount + 1, newCount);

	}
}
