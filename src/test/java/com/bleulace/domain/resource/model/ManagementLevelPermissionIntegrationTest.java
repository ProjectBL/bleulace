package com.bleulace.domain.resource.model;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.Send;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.command.CrmCommandFactory;
import com.bleulace.domain.management.command.AssignManagersCommand;
import com.bleulace.domain.management.command.ManagementCommandFactory;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.Project;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.Locator;

public class ManagementLevelPermissionIntegrationTest implements
		IntegrationTest, CommandGatewayAware
{
	@Autowired
	private ManagementCommandFactory mgtFactory;

	@Autowired
	private CrmCommandFactory crmFactory;

	String resourceId;

	String username;

	@Before
	public void createResources()
	{
		username = crmFactory.createAccount().getUsername();

		mgtFactory.createProject().getId();
		resourceId = Locator.locate(Project.class).getId();
	}

	@Test
	public void testEnumOn()
	{
		Permission p = ManagementLevel.OWN.on(resourceId);
		Permission q = new ResourcePermission(resourceId);
		Assert.assertTrue(p.implies(q));

		q = ManagementLevel.MIX;
		Assert.assertTrue(q.implies(p));
	}

	@Test
	public void testAssignments()
	{
		login();
		final Subject s = SecurityUtils.getSubject();
		Assert.assertTrue(s.isAuthenticated());
		addPermissions(SecurityUtils.getSubject().getId());
		SecurityUtils.getSubject().checkPermission(
				ManagementLevel.OWN.on(resourceId));
	}

	@Send
	private AssignManagersCommand addPermissions(String userId)
	{
		return new AssignManagersCommand(resourceId, ManagementLevel.OWN,
				userId);
	}

	private void login()
	{
		SecurityUtils.getSubject().login(username,
				CrmCommandFactory.ACCOUNT_PASSWORD);
	}

	public Project getResource()
	{
		return EntityManagerReference.load(Project.class, resourceId);
	}
}
