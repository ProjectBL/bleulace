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
import com.bleulace.domain.AuthenticatingTest;
import com.bleulace.domain.management.model.Project;
import com.bleulace.utils.Locator;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class ProjectCommandTest extends AuthenticatingTest implements
		CommandGatewayAware
{
	@Autowired
	@Qualifier("createProjectCommand")
	private CreateProjectCommand createProjectCommand;

	@Test
	public void testCreateProjectCommand() throws Exception
	{
		sendAndWait(createProjectCommand);
		Assert.assertTrue(Locator.exists(Project.class));
	}
}