package com.bleulace.mgt.presentation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.AddBundleCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectPresentationTest implements CommandGatewayAware
{
	@Autowired
	private AddBundleCommand addBundleCommand;

	@Test
	public void testProjectFinder()
	{
		gateway().send(addBundleCommand);
		ProjectDTO dto = ProjectDTO.FINDER.findById(addBundleCommand
				.getParentId());
		Assert.assertNotNull(dto.getId());
		Assert.assertNotNull(dto.getTitle());
		Assert.assertNotNull(dto.getAssignments());
	}
}
