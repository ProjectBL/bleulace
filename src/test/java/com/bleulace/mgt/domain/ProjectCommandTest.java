package com.bleulace.mgt.domain;

import javax.annotation.PostConstruct;

import junit.framework.Assert;

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
import com.bleulace.mgt.application.command.AddCommentCommand;
import com.bleulace.mgt.application.command.AssignManagerCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.utils.jpa.EntityManagerReference;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectCommandTest implements CommandGatewayAware
{
	@Autowired
	private CreateProjectCommand createProjectCommand;

	@Autowired
	private AssignManagerCommand addManagerCommand;

	@Autowired
	private AddBundleCommand addBundleCommand;

	@Autowired
	private AddCommentCommand addCommentCommand;

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
		CreateProjectCommand command = new CreateProjectCommand();
		command.setTitle("Project Name");
		gateway().send(command);
		Assert.assertEquals(count + 1, finder.count());
	}

	@Test
	public void testAddManagerCommand()
	{
		Project project = finder.findOne(addManagerCommand.getId());
		long managerCount = project.getAssignees().size();
		gateway().send(addManagerCommand);
		project = finder.findOne(addManagerCommand.getId());
		Assert.assertEquals(managerCount + 1, project.getAssignees().size());
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
	public void testAddCommentCommand()
	{
		String id = addCommentCommand.getId();
		int size = finder.findOne(id).getComments().size();
		gateway().send(addCommentCommand);
		Assert.assertEquals(size + 1, finder.findOne(id).getComments().size());
	}
}