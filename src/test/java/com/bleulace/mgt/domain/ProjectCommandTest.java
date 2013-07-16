package com.bleulace.mgt.domain;

import javax.annotation.PostConstruct;

import junit.framework.Assert;

import org.axonframework.domain.IdentifierFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.AddBundleCommand;
import com.bleulace.mgt.application.command.AddManagerCommand;
import com.bleulace.mgt.application.command.AddTaskCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.utils.EntityManagerReference;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectCommandTest implements CommandGatewayAware
{
	@Autowired
	private CreateProjectCommand createProjectCommand;

	@Autowired
	private AddManagerCommand addManagerCommand;

	@Autowired
	private AddBundleCommand addBundleCommand;

	@Autowired
	private AddTaskCommand addTaskCommand;

	private JpaRepository<Project, String> finder;

	@PostConstruct
	protected void init()
	{
		finder = new SimpleJpaRepository<Project, String>(Project.class,
				EntityManagerReference.get());
	}

	@Before
	public void before()
	{
		gateway().send(createProjectCommand);
	}

	@Test
	public void testCreateProjectCommand()
	{
		long count = finder.count();
		String id = IdentifierFactory.getInstance().generateIdentifier();
		CreateProjectCommand command = new CreateProjectCommand(id);
		command.setTitle("Project Name");
		gateway().send(command);
		Assert.assertEquals(count + 1, finder.count());
	}

	@Test
	public void testAddManagerCommand()
	{
		Project project = finder.findOne(addManagerCommand.getId());
		long managerCount = project.getManagers().size();
		gateway().send(addManagerCommand);
		Assert.assertEquals(managerCount + 1,
				finder.findOne(addManagerCommand.getId()).getManagers().size());
	}

	@Test
	public void testAddBundleCommand()
	{
		Project project = finder.findOne(addBundleCommand.getParentId());
		long bundleCount = project.getBundles().size();
		gateway().send(addBundleCommand);
		Assert.assertEquals(bundleCount + 1,
				finder.findOne(addBundleCommand.getParentId()).getBundles()
						.size());
	}

	@Test
	public void testAddTaskCommand()
	{
		String id = addTaskCommand.getBundleId();
		long taskCount = finder.findOne(id).getTasks().size();
		gateway().send(addTaskCommand);
		Assert.assertEquals(taskCount + 1, finder.findOne(id).getTasks().size());
	}
}